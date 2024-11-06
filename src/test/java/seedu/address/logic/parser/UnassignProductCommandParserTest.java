package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_REQUIRED_PREFIXES;
import static seedu.address.logic.Messages.MESSAGE_UNEXPECTED_PREAMBLE;
import static seedu.address.logic.commands.CommandTestUtil.PRODUCT_DESC_APPLE_PIE;
import static seedu.address.logic.commands.CommandTestUtil.SUPPLIER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRODUCT_APPLE_PIE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRODUCT_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UnassignProductCommand;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;

public class UnassignProductCommandParserTest {
    private UnassignProductCommandParser parser = new UnassignProductCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = PRODUCT_DESC_APPLE_PIE + SUPPLIER_DESC_AMY;
        ProductName productName = new ProductName(VALID_PRODUCT_APPLE_PIE);
        Name supplierName = new Name(VALID_NAME_AMY);

        UnassignProductCommand expectedCommand = new UnassignProductCommand(productName, supplierName);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingProductPrefix_failure() {
        // missing pr/ prefix
        String userInput = VALID_PRODUCT_APPLE_PIE + SUPPLIER_DESC_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_MISSING_REQUIRED_PREFIXES,
                UnassignProductCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingSupplierPrefix_failure() {
        // missing su/ prefix
        String userInput = PRODUCT_DESC_APPLE_PIE + VALID_NAME_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_MISSING_REQUIRED_PREFIXES,
                UnassignProductCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingBothPrefixes_failure() {
        // missing both pr/ and su/ prefixes
        String userInput = VALID_PRODUCT_APPLE_PIE + VALID_NAME_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_MISSING_REQUIRED_PREFIXES,
                UnassignProductCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // preamble should not exist
        String userInput = "extra " + PRODUCT_DESC_APPLE_PIE + SUPPLIER_DESC_AMY;
        assertParseFailure(parser, userInput, String.format(MESSAGE_UNEXPECTED_PREAMBLE,
                UnassignProductCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateProductPrefix_failure() {
        // duplicate pr/ prefix
        String userInput = PRODUCT_DESC_APPLE_PIE + " " + PREFIX_PRODUCT_NAME + "AnotherProduct" + SUPPLIER_DESC_AMY;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PRODUCT_NAME));
    }
}
