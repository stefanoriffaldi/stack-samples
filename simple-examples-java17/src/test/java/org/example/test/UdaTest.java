package org.example.test;


import org.example.UdaService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UdaTest {

    private final UdaService service = new UdaService();

    @Test
    void udaReferenceSafe() {
        // given
        // when / then
        assertThat(service.udaReferenceSafe(Collections.emptyList(), "UDA1"))
                .isNull();
        // given
        UdaService.Uda uda = new UdaService.Uda("UDA1");
        List<UdaService.Uda> inputList = Collections.singletonList(uda);
        // when / then
        assertThat(service.udaReferenceSafe(inputList, "UDA1"))
                .isNull();
        // given
        uda.setReferences(new ArrayList<>());
        // when / then
        assertThat(service.udaReferenceSafe(inputList, "UDA1"))
                .isNull();
        // given
        uda.getReferences().add(new UdaService.Uda("UDA2"));
        // when / then
        assertThat(service.udaReferenceSafe(inputList, "UDA1"))
                .isNotNull()
                .isEqualTo("UDA2");
    }
}