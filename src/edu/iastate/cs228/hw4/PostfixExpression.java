package edu.iastate.cs228.hw4;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class evaluates a postfix expression using one stack.    
 *
 */

import java.util.HashMap;
import java.util.NoSuchElementException; 

public class PostfixExpression 
{
	private String postfixExpression; 	      // postfix expression to evaluate
	private PureStack<Integer> operandStack;  // stack of operands

	private int leftOperand;                  // left operand for the current evaluation step             
	private int rightOperand;                 // right operand for the current evaluation step	
	
	private HashMap<Character, Integer> varTable; // hash table to store variables in the 
	  											  // infix expression and their values 

	/**
	 * Constructor stores the input postfix string and initializes the operand stack.
	 * @param st  input postfix string. 
	 * @param varTbl  hash table that stores variables from the postfix string and their values.
	 */
	public PostfixExpression (String st, HashMap<Character, Integer> varTbl)
	{
		// TO DO
	}
	

	// Default constructor 
	public PostfixExpression ()
	{
		postfixExpression = null; 
		varTable = null; 
	}
	

	/**
	 * Outputs the postfix expression according to the format in the project description.
	 * Generates an empty string if the postfix expression is invalid. Also in this case handle 
	 * the exception throw by evaluate() by printing out the corresponding error message. 
	 */
	public String toString()
	{
		// TO DO 
		return null; 
	}
	

	/**
	 * Resets the postfix expression. 
	 * @param st
	 */
	public void resetPostfix (String st)
	{
		postfixExpression = st; 
	}


	/**
     * Scan the postfixExpression and carry out the following:  
     *    1. Whenever an integer is encountered, push it onto operandStack.
     *    2. Whenever an operator is encountered, invoke it on the two elements popped from  
     *       operandStack,  and push the result back onto the stack.  
     *    3. If a character that is not a digit, an operator, or a blank space is encountered, stop 
     *       the evaluation. 
     *       
     * @return value of the postfix expression 
     * @throws ExpressionFormatException with one of the messages below:  
     *           -- "Invalid character" if encountering a character that is not a digit, an operator
     *              or a whitespace (blank, tab); 
     *           --	"Too many operands" if operandStack is non-empty at the end of evaluation; 
     *           -- "Too many operators" if getOperands() throws NoSuchElementException; 
     *           -- "Divide by zero" if division or modulo is the current operation and rightOperand == 0;
     *           -- "0^0" if the current operation is "^" and leftOperand == 0 and rightOperand == 0;
     *           -- self-defined message if the error is not one of the above.
     *           
     *         UnassignedVariableException if the operand as a variable does not have a value stored
     *            in the hash table.  In this case, the exception is thrown with the message
     *            
     *           -- "Variable <name> was not assigned a value", where <name> is the name of the variable.  
     *           
     */
	public int evaluate() throws ExpressionFormatException, UnassignedVariableException 
    {
    	// TO DO 
		return 0;  // TO MODIFY
    }
	

    /**
     * 
     * Pops the right and left operands from operandStack, and assign them to rightOperand 
     * and leftOperand, respectively. The stack must have at least two entries.  Otherwise, 
     * throws NoSuchElementException.  
     */
	private void getOperands() throws NoSuchElementException 
	{
		// TO DO 
	}


	/**
	 * Computes "leftOperand op rightOprand". 
	 * @param op operator that acts on leftOperand and rightOperand. 
	 * @return
	 */
	private int compute(char op)  
	{
		// TO DO 
		return 0;  // TO MODIFY 
	}

}
