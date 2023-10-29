package com.project.ngit.Commands;

import java.io.Serializable;

public record FileStatus(String path, String activeTimestamp, String initialTimestamp, boolean isCommitted) implements Serializable {}