package com.codersfactory.flashcards;

import com.codersfactory.flashcards.dto.CreateFlashcardCollectionDto;
import com.codersfactory.flashcards.dto.FlashcardCollectionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FlashcardCollectionsServiceTest {

    @Mock
    FlashcardCollectionsRepository repository;

    @InjectMocks
    FlashcardCollectionsService service;

    @Test
    @DisplayName("Should initialize mock objects properly")
    public void shouldVerifyMockObjectsNotNull() {
        assertNotNull(repository);
        assertNotNull(service);
    }

    @Test
    public void mapDtoTest() {
        CreateFlashcardCollectionDto dto = new CreateFlashcardCollectionDto("title");
        FlashcardCollection collection = service.mapDTO(dto);
        assertEquals(dto.title(), collection.getTitle());
    }

    @Test
    public void toDtoTest() {
        FlashcardCollection collection = new FlashcardCollection();
        collection.setTitle("title");
        for (int i = 0; i < 10; i++) {
            collection.addCard(new Flashcard(Integer.toUnsignedLong(i), "front"+i, "back"+i, collection));
        }
        FlashcardCollectionDto randomized = service.toDTO(collection);
        FlashcardCollectionDto sorted = service.mapToSortedDto(collection);

        assertNotNull(randomized);
        assertEquals(randomized.title(), collection.getTitle());
        assertEquals(10, randomized.flashcards().size());

        for (int i = 0; i < collection.getSize(); i++) {
            assertEquals(sorted.flashcards().get(i).id(), Integer.toUnsignedLong(i));
        }
    }
}
