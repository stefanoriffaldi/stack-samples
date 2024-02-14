package org.example.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.OptionalNull.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OptionalNullTest {
    @Mock
    private CatalogStore catalogStore;
    private CatalogService optionalNull;

    @BeforeEach
    void setUp() {
        optionalNull = new CatalogService(catalogStore);

        when(catalogStore.getCatalog(1)).thenReturn(null);
        when(catalogStore.getCatalog(2)).thenReturn(new CatalogDO(2, true));
        when(catalogStore.getCatalog(3)).thenReturn(new CatalogDO(3, false));
    }

    @Test
    void getOppositePriceConversionOfNullable() {
        assertThat(optionalNull.getOppositePriceConversionOfNullable(1))
                .isFalse();

        assertThat(optionalNull.getOppositePriceConversionOfNullable(2))
                .isTrue();

        assertThat(optionalNull.getOppositePriceConversionOfNullable(3))
                .isFalse();
    }

    @Test
    void getOppositePriceConversion() {
        assertThatThrownBy(() -> optionalNull.getOppositePriceConversion(1))
                .isExactlyInstanceOf(NullPointerException.class)
                .hasNoCause();

        assertThat(optionalNull.getOppositePriceConversion(2))
                .isTrue();

        assertThat(optionalNull.getOppositePriceConversion(3))
                .isFalse();
    }
}