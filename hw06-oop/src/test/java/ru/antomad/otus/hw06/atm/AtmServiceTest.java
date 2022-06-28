package ru.antomad.otus.hw06.atm;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;
import ru.antomad.otus.hw06.paper.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtmServiceTest {

    private Atm atm;

    @BeforeEach
    public void warmUp() {
        this.atm = new AtmService();
    }

    @Test
    public void shouldAcceptListOfPapersAndReturnBalance() {
        atm.put(List.of(
                new OneHundred(),
                new TwoHundred(),
                new FiveHundred(),
                new OneThousand(),
                new TwoThousand(),
                new FiveThousand()
        ));

        assertEquals(8800, atm.balance());
    }

    @Test
    public void shouldReturnListOfPapersBySum() {
        atm.put(List.of(
                new OneHundred(),
                new TwoHundred(),
                new FiveHundred(),
                new OneThousand(),
                new TwoThousand(),
                new FiveThousand()
        ));

        List<Paper> papers = atm.pull(8800);

        assertEquals(6, papers.size());
    }

    @Test
    public void shouldThrowExceptionWhenUnableToGetSum() {
        atm.put(List.of(
                new OneHundred(),
                new TwoHundred(),
                new FiveHundred(),
                new OneThousand(),
                new TwoThousand(),
                new FiveThousand()
        ));

        Exception exception = assertThrows(RuntimeException.class, () -> atm.pull(8900));

        assertEquals("Couldn't find required sum", exception.getMessage());

        assertEquals(8800, atm.balance());
    }
}