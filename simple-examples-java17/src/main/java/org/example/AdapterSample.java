package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

/**
 * Classic example of Adapter. Some classes you don't control, related by some fields but without a programming link.
 * To avoid write redundant business logic code, use adapter technique
 */
public class AdapterSample {
    public static Adapted adapt(Object toAdapt) {
        if (toAdapt == null) {
            return null;
        }
        if (toAdapt instanceof ToAdapt1) {
            return new Adapter1((ToAdapt1) toAdapt);
        } else if (toAdapt instanceof ToAdapt2) {
            return new Adapter2((ToAdapt2) toAdapt);
        }
        throw new IllegalArgumentException("Cannot adapt to " + Adapted.class + " an object of type " + toAdapt.getClass());
    }

    public void useFields(Adapted adapted) {
        System.out.println(adapted.getField1());
        System.out.println(adapted.getField2());
    }

    public interface Adapted {
        String getField1();

        String getField2();
    }

    @Getter
    @Setter
    public static class ToAdapt1 {
        private String field1;
        private String field2;
    }

    @Getter
    @Setter
    public static class ToAdapt2 {
        private String field1;
        private String field2;
    }

    private record Adapter1(@Delegate(types = Adapted.class) ToAdapt1 toAdapt1) implements Adapted {
    }

    private record Adapter2(@Delegate(types = Adapted.class) ToAdapt2 toAdapt2) implements Adapted {
    }
}
