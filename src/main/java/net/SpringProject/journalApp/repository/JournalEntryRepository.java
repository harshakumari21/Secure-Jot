package net.SpringProject.journalApp.repository;

import net.SpringProject.journalApp.entity.JournalEntry;
import net.SpringProject.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
