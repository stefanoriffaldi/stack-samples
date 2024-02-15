package org.example;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfStampAnnotation;
import lombok.SneakyThrows;
import lombok.experimental.StandardException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Question: <a href="https://stackoverflow.com/q/77981954/11289119">Generating PDF file with types (not format) per page</a>
 *
 * @author stefano-riffaldi
 */
@Slf4j
public class ProfilePrinting {

    @SneakyThrows
    public static void main(String[] args) {
        // Create sample empty PDF document with 5 pages
        Path sourcePdfPath = Files.createTempFile("tmp-source-", ".pdf");
        try (
                OutputStream outputStream = Files.newOutputStream(sourcePdfPath);
                PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream))
        ) {
            pdfDocument.addNewPage();
            pdfDocument.addNewPage();
            pdfDocument.addNewPage();
            pdfDocument.addNewPage();
            pdfDocument.addNewPage();
            pdfDocument.addNewPage();
        }
        // set profile metadata to PDF document
        Path profiledPdfPath = Files.createTempFile("tmp-dest-", ".pdf");
        log.info("Source file: {}", profiledPdfPath);
        log.info("Dest   file: {}", profiledPdfPath);
        try (
                InputStream inputStream = Files.newInputStream(sourcePdfPath);
                OutputStream outputStream = Files.newOutputStream(profiledPdfPath);
                Profiler profiler = new ITextProfiler(inputStream, outputStream)
        ) {
            profiler.setProfiles(Arrays.asList(Profile.A, Profile.B, Profile.B, Profile.B, null, Profile.C));
        }
        // printPage PDF document with profiles
        try (
                InputStream inputStream = Files.newInputStream(profiledPdfPath);
                Printer printer = new ITextProfiledPrinter(inputStream)
        ) {
            printer.printAll();
        }
    }

    public enum Profile {
        A, B, C, D, DEFAULT
    }

    public interface Printer extends AutoCloseable {

        void printAll();

        void printPage(int page);

        void printRange(int fromInclusive, int toInclusive);
    }

    public interface Profiler extends AutoCloseable {

        List<Profile> getProfiles();

        void setProfiles(List<Profile> profiles);
    }

    public static class ITextProfiledPrinter implements Printer {
        private final PdfDocument pdfDocument;
        private final List<Profile> profiles;

        public ITextProfiledPrinter(InputStream is) {
            try {
                pdfDocument = new PdfDocument(new PdfReader(is));
            } catch (IOException e) {
                throw new PdfException(e);
            }
            profiles = new ITextProfiler(pdfDocument).getProfiles();
        }

        @Override
        public void close() {
            pdfDocument.close();
        }

        @Override
        public void printAll() {
            printRange(1, pdfDocument.getNumberOfPages());
        }

        @Override
        public void printPage(int page) {
            // implement printPage logic
            printRange(page, page);
        }

        @Override
        public void printRange(int fromInclusive, int toInclusive) {
            for (int page = fromInclusive; page <= toInclusive; page++) {
                log.debug("Printing page {} with profile '{}'", page, profiles.get(page - 1));
            }
        }
    }

    public static class ITextProfiler implements Profiler {
        private static final String PREFIX = "PrintProfile_";
        private final PdfDocument pdfDocument;

        private ITextProfiler(PdfDocument pdfDocument) { // scope: package-private
            this.pdfDocument = pdfDocument;
        }

        public ITextProfiler(InputStream is, OutputStream os) {
            try {
                pdfDocument = new PdfDocument(new PdfReader(is), new PdfWriter(os));
            } catch (IOException e) {
                throw new PdfException(e);
            }
        }

        @Override
        public void close() {
            log.debug("Closing and saving document");
            pdfDocument.close();
        }

        @Override
        public List<Profile> getProfiles() {
            List<Profile> profiles = new ArrayList<>();
            for (int i = 0; i < pdfDocument.getNumberOfPages(); i++) {
                List<PdfAnnotation> annotations = pdfDocument.getPage(i + 1).getAnnotations();
                log.debug("Page {}, annotations: {}", i + 1, annotations.size());
                Profile profile = annotations.stream()
                        .map(this::getProfile).filter(Objects::nonNull)
                        .findFirst().orElse(null);
                profiles.add(profile);
            }
            return profiles;
        }

        @Override
        public void setProfiles(List<Profile> profiles) {
            for (int i = 0; i < profiles.size(); i++) {
                Profile profile = profiles.get(i);
                log.debug("Adding annotation to page {} with profile '{}'", i + 1, profile);
                addAnnotation(pdfDocument.getPage(i + 1), profile);
            }
        }

        private void addAnnotation(PdfPage page, Profile profile) {
            if (profile == null) {
                return;
            }
            // customize type of annotation to choose proper type, appearance, etc
            Rectangle rect = new Rectangle(10, 10, 100, 100);
            PdfAnnotation annotation = new PdfStampAnnotation(rect)
                    .setName(new PdfString(PREFIX + profile.name()))
                    .setFlags(PdfAnnotation.HIDDEN);
            // PDF Reference 1.4: https://opensource.adobe.com/dc-acrobat-sdk-docs/pdfstandards/pdfreference1.4.pdf
            // 8.4.2 Annotation Flags
            // Hidden (PDF 1.2): If set, do not display or print the annotation or allow it to interact
            // with the user, regardless of its annotation type or whether an annotation
            // handler is available. In cases where screen space is limited, the ability to hide
            // and show annotations selectively can be used in combination with appearance
            // streams (see Section 8.4.4, “Appearance Streams”) to display auxiliary pop-up
            // information similar in function to online help systems. (See implementation
            // note 63 in Appendix H.)
            page.addAnnotation(annotation);
            log.debug("Added annotation to page {} with profile '{}'", page.getDocument().getPageNumber(page), profile);
        }

        private Profile getProfile(PdfAnnotation annotation) {
            // customize behavior to get the profile, if the annotation contains that information
            String titleString = annotation.getName().toString();
            try {
                if (titleString.startsWith(PREFIX)) {
                    return Profile.valueOf(titleString.substring(PREFIX.length()));
                }
            } catch (IllegalArgumentException e) {
                // do nothing, just return null
            }
            return null;
        }
    }

    @StandardException
    private static class PdfException extends RuntimeException {
    }
}
