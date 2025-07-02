package net.SpringProject.journalApp.controller;

import net.SpringProject.journalApp.entity.JournalEntry_1;
import net.SpringProject.journalApp.service.JournalEntryService_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal-sql")
public class JournalEntryController_1 {

    @Autowired
    private JournalEntryService_1 journalEntryService;

    @GetMapping
    public List<JournalEntry_1> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public boolean createEntry(@RequestBody JournalEntry_1 myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return true;
    }

    @GetMapping("id/{myId}")
    public JournalEntry_1 getJournalEntryById(@PathVariable Long myId) {
        return journalEntryService.findById(myId).orElse(null);
    }

    @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable Long myId) {
        journalEntryService.deleteById(myId);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry_1 updateJournalEntryById(@PathVariable Long id, @RequestBody JournalEntry_1 newEntry) {
        JournalEntry_1 old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
                old.setTitle(newEntry.getTitle());
            }
            if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
                old.setContent(newEntry.getContent());
            }
            journalEntryService.saveEntry(old);
        }
        return old;
    }
}

