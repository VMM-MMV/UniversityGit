package com.project.ngit.Commands;

import java.io.Serializable;

public record FileStatus(String activeTimestamp, String initialTimestamp) implements Serializable {}
