package tech.chorume.bot.core.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation to indicate GUILD command builder. GUILD commands do not need to wait for Discord to register the change
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GuildCommandBuilder {
}
