package net.SpringProject.journalApp.controller;

import net.SpringProject.journalApp.entity.JournalEntry;
import net.SpringProject.journalApp.entity.User;
import net.SpringProject.journalApp.service.JournalEntryService;
import net.SpringProject.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService; // instance injected

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (!all.isEmpty() && all != null) { // 1st change -> change the positions
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // @GetMapping
    // public ResponseEntity<?> getAll() {
    // List<JournalEntry> all = journalEntryService.getAll();
    // if(all != null && !all.isEmpty()){
    // return new ResponseEntity<>(all, HttpStatus.OK);
    // }
    // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    // }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) { // its like saying he spring
                                                                                         // pls take data from request
                                                                                         // and turn it into a java
                                                                                         // object that i can use in my
                                                                                         // code.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName(); // 2nd Change -> take out the username out of try
        try {
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);

        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if (!collect.isEmpty()) {
            try {
                // ObjectId objectId = new ObjectId(myId); // ✅ Convert string to ObjectId
                Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
                if (journalEntry.isPresent()) {
                    return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
                }
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // malformed ObjectId string
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalEntryService.deleteById(myId, userName);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("id/{userName}/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry,
            @PathVariable String userName) {
        JournalEntry old = journalEntryService.findById(id).orElse(null);
        if (old != null) {
            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                    : old.getTitle());
            old.setContent(
                    newEntry.getContent() != null && !newEntry.equals("") ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
