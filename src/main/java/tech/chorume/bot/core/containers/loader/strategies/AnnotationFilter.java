package tech.chorume.bot.core.containers.loader.strategies;

import tech.chorume.bot.core.interfaces.ComponentFilter;

import java.lang.annotation.Annotation;

public class AnnotationFilter implements ComponentFilter {
    Class<? extends Annotation> assignedAnnotation;
    public AnnotationFilter(Class<? extends Annotation> assignedAnnotation) {
        this.assignedAnnotation = assignedAnnotation;
    }
    public boolean match(Class<?> clazz) {
        return clazz.isAnnotationPresent(assignedAnnotation);
    }
}
