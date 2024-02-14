package org.example;

import java.util.Optional;

/**
 * Question: <a href="https://stackoverflow.com/q/77995735/11289119">Why do I get a null here?</a>
 *
 * @author stefano-riffaldi
 */
public class OptionalNull {
    public static class CatalogService {
        private final CatalogStore catalogStore;

        public CatalogService(CatalogStore catalogStore) {
            this.catalogStore = catalogStore;
        }

        public boolean getOppositePriceConversion(Integer catalogId) {
            return Optional.of(catalogStore.getCatalog(catalogId))
                    .map(CatalogDO::oppositePriceConversion)
                    .orElse(Boolean.FALSE);
        }
        public boolean getOppositePriceConversionOfNullable(Integer catalogId) {
            return Optional.ofNullable(catalogStore.getCatalog(catalogId))
                    .map(CatalogDO::oppositePriceConversion)
                    .orElse(Boolean.FALSE);
        }
    }

    public interface CatalogStore {
        CatalogDO getCatalog(Integer catalogId);
    }

    public record CatalogDO(Integer id, Boolean oppositePriceConversion) {}
}
