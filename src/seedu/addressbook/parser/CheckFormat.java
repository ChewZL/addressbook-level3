package seedu.addressbook.parser;

import static seedu.addressbook.common.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.*;
import java.util.regex.*;

import seedu.addressbook.commands.*;
import seedu.addressbook.data.exception.IllegalValueException;


/**
 *This class checks the format of user input 
 *
 */
public class CheckFormat {
    private Matcher matcher;
    public CheckFormat(String args, Pattern format){
        matcher = format.matcher(args.trim());
    }
    public boolean validateFormat(){
        // Validate arg string format
        return matcher.matches();
    }
    public Command processAddCommand(){
        try {
            return new AddCommand(
                    matcher.group("name"),

                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    getTagsFromArgs(matcher.group("tagArguments"))
                    );
        } catch (IllegalValueException e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

    public Command processEditCommand(int targetIndex){
        try{
            return new EditCommand(
                    targetIndex,
                    matcher.group("phone"),
                    isPrivatePrefixPresent(matcher.group("isPhonePrivate")),

                    matcher.group("email"),
                    isPrivatePrefixPresent(matcher.group("isEmailPrivate")),

                    matcher.group("address"),
                    isPrivatePrefixPresent(matcher.group("isAddressPrivate")),

                    getTagsFromArgs(matcher.group("tagArguments"))
                    );
        }
        catch(IllegalValueException e){
            return new IncorrectCommand(e.getMessage());
        }
    }
    
    public int getIndex(){
        return Integer.parseInt(matcher.group("targetIndex"));
    }
    
    public String[] getKeywords(){
        final String[] keywords = matcher.group("keywords").split("\\s+");
        return keywords;
    }



    /**
     * Checks whether the private prefix of a contact detail in the add command's arguments string is present.
     */
    private static boolean isPrivatePrefixPresent(String matchedPrefix) {
        return matchedPrefix.equals("p");
    }


    /**
     * Extracts the new person's tags from the add command's tag arguments string.
     * Merges duplicate tag strings.
     */
    private static Set<String> getTagsFromArgs(String tagArguments) throws IllegalValueException {
        // no tags
        if (tagArguments.isEmpty()) {
            return Collections.emptySet();
        }
        // replace first delimiter prefix, then split
        final Collection<String> tagStrings = Arrays.asList(tagArguments.replaceFirst(" t/", "").split(" t/"));
        return new HashSet<>(tagStrings);
    }

}
