package seedu.addressbook.storage;

import java.nio.file.Path;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.storage.StorageFile.StorageOperationException;

public abstract class Storage {
    /**
     * Saves all data to this storage file.
     */
    public abstract void save(AddressBook addressBook) throws Exception;
    /**
     * Loads data from this storage file.
     */
    public abstract AddressBook load() throws Exception;
    /**
     * @return path in String format
     */
    public abstract String getPath();
}
