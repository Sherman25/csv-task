package com.example.csvtask.services;

import com.example.csvtask.domain.Card;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * CSV file service
 *
 * @author Otabek Sherman
 * @since 2022/09/20
 */
public interface CSVFileService {

    public List<Card> readFile(MultipartFile file);

    public List<Card> getCardsFromDbSortedAlphabetically();
    public List<Card> getCardsFromDbSortedNumerically();
    public void validateFile(MultipartFile file);
    public void storeCardsDataToDB(List<Card> cards);
    public List<String> sortNames(List<String> names);
    public List<Integer> sortCardNumbers(List<Integer> numbers);
}
