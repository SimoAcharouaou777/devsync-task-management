package com.youcode.devsync.service;

import com.youcode.devsync.model.Tag;
import com.youcode.devsync.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TagServiceTest {

    @Mock
    TagRepository tagRepository;
    @InjectMocks
    TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTags() {
        Tag tag1 = new Tag();
        Tag tag2 = new Tag();
        Tag tag3 = new Tag();
        tag1.setName("Urgent");
        tag2.setName("Important");
        tag3.setName("Optional");

        List<Tag> mockTagList = Arrays.asList(tag1, tag2, tag3);
        when(tagRepository.findAll()).thenReturn(mockTagList);

        List<Tag> result = tagService.getAllTags();
        assertEquals(3, result.size());
        assertEquals("Urgent", result.get(0).getName());
        assertEquals("Important", result.get(1).getName());
        assertEquals("Optional", result.get(2).getName());
    }

    @Test
    void findById() {
    }
}