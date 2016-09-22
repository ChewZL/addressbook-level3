package seedu.addressbook.storage;

import seedu.addressbook.data.AddressBook;
import seedu.addressbook.storage.StorageFile.InvalidStorageFilePathException;

public class StorageStub{
    private Storage storage;
    public StorageStub(String filepath)throws InvalidStorageFilePathException{
        storage = new StorageFile(filepath);
    }
    /**
     * @return storage
     */
    public Storage getStorage(){
        return storage;
    }
    /**
     * Save method does nothing
     */
    public void save(AddressBook addressBook) {
    }
}
