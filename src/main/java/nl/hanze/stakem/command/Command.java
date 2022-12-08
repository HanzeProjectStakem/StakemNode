package nl.hanze.stakem.command;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, property="@type")
public interface Command {

}
