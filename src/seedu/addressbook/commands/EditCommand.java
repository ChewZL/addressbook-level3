package seedu.addressbook.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.addressbook.common.Messages;
import seedu.addressbook.data.exception.IllegalValueException;
import seedu.addressbook.data.person.*;
import seedu.addressbook.data.person.UniquePersonList.PersonNotFoundException;
import seedu.addressbook.data.tag.Tag;
import seedu.addressbook.data.tag.UniqueTagList;

public class EditCommand extends Command{

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n" + "Edits a person in the address book. "
            + "Contact details can be marked private by prepending 'p' to the prefix.\n\t"
            + "Parameters: INDEX [p]p/PHONE [p]e/EMAIL [p]a/ADDRESS  [t/TAG]...\n\t"
            + "Example: " + COMMAND_WORD
            + " 1 p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "Person edited: %1$s";
    public static final String MESSAGE_NO_SUCH_PERSON = "This person does not exist in the address book";

    private Person toEdit;
    private final int editNum;
    private final String phone; 
    private final boolean isPhonePrivate;
    private final String email; 
    private final boolean isEmailPrivate;
    private final String address; 
    private final boolean isAddressPrivate;
    private final Set<Tag> tagSet;

    /**
     * Convenience constructor using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public EditCommand(int targetVisibleIndex,
            String phone, boolean isPhonePrivate,
            String email, boolean isEmailPrivate,
            String address, boolean isAddressPrivate,
            Set<String> tags) throws IllegalValueException {
        super(targetVisibleIndex);
        editNum = targetVisibleIndex;
        tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.phone = phone;
        this.isPhonePrivate = isPhonePrivate;
        this.email = email;
        this.isEmailPrivate = isEmailPrivate;
        this.address = address;
        this.isAddressPrivate = isAddressPrivate;
        
    }
    
    public ReadOnlyPerson getPerson() {
        return toEdit;
    }

    @Override
    public CommandResult execute() {
        try {
            final ReadOnlyPerson target = getTargetPerson();
            toEdit = new Person(
                    target.getName(),
                    new Phone(phone, isPhonePrivate),
                    new Email(email, isEmailPrivate),
                    new Address(address, isAddressPrivate),
                    new UniqueTagList(tagSet)
            );
            addressBook.removePerson(target);
            DeleteCommand deleted = new DeleteCommand(editNum);
            deleted.execute();
 
            AddCommand added = new AddCommand(toEdit);
            added.execute();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toEdit));
        }catch (IndexOutOfBoundsException ie) {
            return new CommandResult(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        catch (PersonNotFoundException e) {
            return new CommandResult(Messages.MESSAGE_PERSON_NOT_IN_ADDRESSBOOK);
        } catch (IllegalValueException e) {
            return new CommandResult(e.getMessage());
        }
    }
}

