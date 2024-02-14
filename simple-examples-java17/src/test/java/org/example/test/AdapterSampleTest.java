package org.example.test;

import org.assertj.core.api.ThrowableAssert;
import org.example.AdapterSample;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AdapterSampleTest {

    @Test
    public void adapt_return_instance_of_Adapted_when_passed_instance_of_recognized_object() {
        // Given recognized object
        AdapterSample.ToAdapt1 toAdapt = new AdapterSample.ToAdapt1();
        // When
        assertThat(AdapterSample.adapt(toAdapt))
                // Then
                .isNotNull()
                .isInstanceOf(AdapterSample.Adapted.class);

        // Given
        AdapterSample.ToAdapt2 toAdapt2 = new AdapterSample.ToAdapt2();
        // When
        assertThat(AdapterSample.adapt(toAdapt))
                // Then
                .isNotNull()
                .isInstanceOf(AdapterSample.Adapted.class);
    }

    @Test
    public void adapt_throw_IllegalArgumentException_when_passed_instance_of_something_else() {
        // Given
        String toAdapt = "";

        // When
        ThrowableAssert.ThrowingCallable raiseThrowable = () -> AdapterSample.adapt(toAdapt);

        // Then
        assertThatThrownBy(raiseThrowable).isExactlyInstanceOf(IllegalArgumentException.class)
                .hasNoCause().hasMessage("Cannot adapt to " + AdapterSample.Adapted.class + " an object of type " + toAdapt.getClass());
    }

    @Test
    public void adapt_return_null_when_passed_null() {
        // Given
        Object toAdapt = null;
        // When
        assertThat(AdapterSample.adapt(toAdapt))
                // Then
                .isNull();
    }
}