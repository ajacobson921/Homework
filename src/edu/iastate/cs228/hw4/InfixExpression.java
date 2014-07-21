package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

import java.util.HashMap;

/**
 * 
 * This class represents an infix expression. It implements infix to postfix conversion using 
 * one stack, and evaluates the postfix equivalent of an infix expression.    
 *
 */

public class InfixExpression 
{
	private String infixExpression;   // the infix expression to convert		
	private String postfixExpression; // the postfix equivalent of infixExpression
	private boolean postfixReady = false; 
	
	private PureStack<Operator> operatorStack; 	  // stack of operators 
	private HashMap<Character, Integer> varTable; // hash table storing variables in the 
												  // infix expression and their values 

	
	
	/**
	 * Constructor stores the input infix string, and initializes the operand stack and 
	 * the hash table.
	 * @param st  input infix string. 
	 * @param varTbl  hash table storing all variables in the infix expression and their values. 
	 */
	public InfixExpression (String st, HashMap<Character, Integer> varTbl)
	{
		// TO DO
	}
	

	// Default constructor 
	public InfixExpression ()
	{
		infixExpression = null; 
	}
	

	/**
	 * Outputs the infix expression according to the format in the project description.
	 */
	public String toString()
	{
		// TO DO  
		return null; 
	}
	
	
	/** 
	 * 
	 * @return the equivalent postfix expression 
	 * Returns a null string if a call to postfix() inside the body (when postfixReady 
	 * == false) throws an exception.
	 */
	public String postfixString() 
	{
		// TO DO
		return null; 
	}


	/**
	 * Resets the infix expression. 
	 * @param st
	 */
	public void resetInfix (String st)
	{
		infixExpression = st; 
	}


	/**
	 * Converts infixExpression to an equivalent postfix string stored at postfixExpression.
	 * If postfixReady == false, the method scans the infixExpression, and does the following: 
	 * 
	 *     1. Skips a whitespace character.
	 *     2. Writes a scanned operand to postfixExpression.
	 *     3. If a scanned operator has a higher input precedence than the stack precedence of 
	 *        the operator on the top of operatorStack, push it onto the stack.   
	 *     4. Otherwise, first calls outputHigherOrEqual() before pushing the scanner operator 
	 *        onto the stack. No push if the scanned operator is ). 
     *     5. Keeps track of the cumulative rank of the infix expression. 
     *     
     *  During the conversion, catches errors in the infixExpression by throwing 
     *  ExpressionFormatException with one of the following messages:
     *  
     *      -- "Operator expected" if the cumulative rank goes above 1;
     *      -- "Operand expected" if the rank goes below 0; 
     *      -- "Missing '('" if scanning a ‘)’ results in popping the stack empty with no '(';
     *      -- "Missing ')'" if a '(' is left unmatched on the stack at the end of the scan; 
     *      -- "Invalid character" if a scanned char is neither a digit nor an operator; 
     *      -- "Negative operand not allowed" if the input operand is negative
     *   
     *   If an error is not one of the above types, throw the exception with a message you define.
     *      
     *  Sets postfixReady to true.  
	 */
	public void postfix() throws ExpressionFormatException
	{
		 // TO DO 
	}
	
	
	/**
	 * This function first calls postfix() to convert infixExpression into postfixExpression. Then 
	 * it creates a PostfixExpression object and calls its evaluate() method (which may throw  
	 * an exception).  It passes exceptions thrown by the evaluate() method of the 
	 * PostfixExpression object upward the chain. 
	 * 
	 * @return value of the infix expression 
	 * @throws ExpressionFormatException, UnassignedVariableException
	 */
	public int evaluate() throws ExpressionFormatException, UnassignedVariableException 
    {
    	// TO DO 
		return 0;  // TO MODIFY
    }


	/**
	 * Pops the operator stack and output as long as the operator on the top of the stack has a 
	 * stack precedence greater than or equal to the input precedence of the current operator op.  
	 * Writes the popped operators to the string postfixExpression. 
	 * 
	 * If op is a ')', and the top of the stack is a '(', also pops '(' from the stack but does 
	 * not write it to postfixExpression. 
	 * 
	 * @param op  Current operator
	 */
	private void outputHigherOrEqual(Operator op)
	{
		// TO DO 
	}
}
