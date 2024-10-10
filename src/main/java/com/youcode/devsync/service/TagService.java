package com.youcode.devsync.service;

import com.youcode.devsync.model.Tag;
import com.youcode.devsync.repository.TagRepository;

import java.util.List;

public class TagService {

    private TagRepository tagRepository = new TagRepository();

    public TagService(){
        tagRepository = new TagRepository();
    }
    public void insertFakeDataIfEmpty() {
        if (tagRepository.isEmpty()) {
            Tag tag1 = new Tag();
            tag1.setName("Urgent");

            Tag tag2 = new Tag();
            tag2.setName("Important");

            Tag tag3 = new Tag();
            tag3.setName("Optional");

            tagRepository.save(tag1);
            tagRepository.save(tag2);
            tagRepository.save(tag3);
            System.out.println("Fake tags added successfully");
        }
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }
    public Tag findById(long id) {return tagRepository.findById(id);}
    public void close() {
        tagRepository.close();
    }
}