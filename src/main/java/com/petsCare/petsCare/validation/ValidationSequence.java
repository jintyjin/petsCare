package com.petsCare.petsCare.validation;

import jakarta.validation.GroupSequence;

@GroupSequence({ValidationGroups.NotBlankGroup.class, ValidationGroups.SizeGroup.class, ValidationGroups.PatternGroup.class})
public interface ValidationSequence {
}
