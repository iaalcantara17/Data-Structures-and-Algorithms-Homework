package edu.njit.cs114.tests;

import edu.njit.cs114.ExpressionEvaluator;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class InvalidExprTest {

    @Test
    public void testInvalidParentheses() {
        assertThrows(Exception.class, () -> ExpressionEvaluator.convertToPostFix(
                ExpressionEvaluator.parseExpr("(4 + 3 * ( 2 ( 7 + 1 ))")));
        assertThrows(Exception.class, () -> ExpressionEvaluator.convertToPostFix(
                ExpressionEvaluator.parseExpr("4 + 3 * ( 2 ( 7 + 1 ) ) )")));
    }
}