package com.example.csvtask.services;

import com.example.csvtask.domain.Card;
import com.example.csvtask.repositories.CardRepository;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CSVFileServiceImplTest {

    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    CSVFileServiceImpl fileService;

    @Test
    void readFileTest() {
        MultipartFile file = buildMultipartFile();
        List<Card> cards = fileService.readFile(file);
        assertTrue(cards.size() > 0);
    }

    @Test
    void validateFileTest_throwsException() {
        MultipartFile file = buildInvalidMultipartFile();
        assertThrows(IllegalArgumentException.class, () ->
                fileService.validateFile(file));
    }

    @Test
    void getCardsFromDbSortedAlphabeticallyTest() {
        List<Card> cards = buildCardListUnsorted();
        when(cardRepository.findAll()).thenReturn(cards);
        List<Card> sortedAlphabetically = fileService
                .getCardsFromDbSortedAlphabetically();
        assertNotEquals(cards, sortedAlphabetically);
    }

    @Test
    void getCardsFromDbSortedNumericallyTest() {
        List<Card> cards = buildCardListUnsorted();
        when(cardRepository.findAll()).thenReturn(cards);
        List<Card> sortedNumerically= fileService
                .getCardsFromDbSortedNumerically();
        assertNotEquals(cards, sortedNumerically);
    }

    @Test
    void sortNamesTest() {
        List<String> names = buildListOfNamesUnsorted();
        assertNotEquals(names, fileService.sortNames(names));
    }

    @Test
    void sortNumbersTest() {
        List<BigInteger> numbers = buildListOfCardNumbersUnsorted();
        assertNotEquals(numbers, fileService.sortCardNumbers(numbers));
    }

    @SneakyThrows
    private MultipartFile buildMultipartFile() {
        File initFile = new File("test-files/test-file.csv");
        InputStream is = new FileInputStream(initFile);
        return new MockMultipartFile("test-file",
                initFile.getName(), "text/csv", is);
    }

    @SneakyThrows
    private MultipartFile buildInvalidMultipartFile() {
        File initFile = new File("db-scripts/database.sql");
        InputStream is = new FileInputStream(initFile);
        return new MockMultipartFile("database",
                initFile.getName(), "text/plain", is);
    }

    private List<Card> buildCardListUnsorted() {
        List<Card> cardList = new ArrayList<>();
        Card card1 = new Card();
        card1.setId(1l);
        card1.setCardNumber(BigInteger.valueOf(123));
        card1.setHolderName("C");
        cardList.add(card1);
        Card card2 = new Card();
        card2.setId(2l);
        card2.setCardNumber(BigInteger.valueOf(345));
        card2.setHolderName("A");
        cardList.add(card2);
        Card card3 = new Card();
        card3.setId(3l);
        card3.setCardNumber(BigInteger.valueOf(213));
        card3.setHolderName("B");
        cardList.add(card3);
        return cardList;
    }

    private List<String> buildListOfNamesUnsorted() {
        List<String> names = new ArrayList<>();
        names.add("C");
        names.add("A");
        names.add("B");
        return names;
    }

    private List<BigInteger> buildListOfCardNumbersUnsorted() {
        List<BigInteger> numbers = new ArrayList<>();
        numbers.add(BigInteger.valueOf(3));
        numbers.add(BigInteger.valueOf(1));
        numbers.add(BigInteger.valueOf(2));
        return numbers;
    }
}
