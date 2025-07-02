package net.SpringProject.journalApp.service;

import net.SpringProject.journalApp.entity.JournalEntry_1;
import net.SpringProject.journalApp.repository.JournalEntryRepository_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService_1 {

    @Autowired
    private JournalEntryRepository_1 journalEntryRepository;

    public void saveEntry(JournalEntry_1 journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry_1> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry_1> findById(Long id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(Long id){
        journalEntryRepository.deleteById(id);
    }
}
