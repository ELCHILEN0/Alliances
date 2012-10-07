package com.JnaniDev.Commands;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates a command. Methods should be marked with this
 * annotation to tell {@link CommandManager} that the method is a command.
 * Note that the method name can actually be anything.
 *
 * @author ELCHILEN0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseCommand {
	/**
	 * A list of aliases for the command. The first alias is the most
	 * important -- it is the main name of the command. (The method name
	 * is never used for anything).
	 *
	 * @return Aliases for a command
	 */
	String[] aliases();

	/**
	 * Usage instruction. Example text for usage could be
	 * <code>[player] [message]</code>.
	 *
	 * @return Usage instructions for a command
	 */
	String usage() default "";

	/**
	 * @return A short description for the command.
	 */
	String desc();

	/**
	 * The minimum number of arguments. This should be 0 or above.
	 *
	 * @return the minimum number of arguments
	 */
	int min() default 0;

	/**
	 * The maximum number of arguments. Use -1 for an unlimited number
	 * of arguments.
	 *
	 * @return the maximum number of arguments
	 */
	int max() default -1;
	
	/**
	 * Allow the command to be run from a player
	 * 
	 * @return can be run as player
	 */
	boolean allowPlayer() default true;
	
	/**
	 * Allow the command to be run from console
	 * 
	 * @return can be run as console
	 */
	boolean allowConsole() default true;
	
	/**
	 * Hide the command
	 * 
	 * @return is hidden
	 */
	boolean hidden() default false;
}