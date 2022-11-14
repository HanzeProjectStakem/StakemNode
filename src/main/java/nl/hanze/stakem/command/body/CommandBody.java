package nl.hanze.stakem.command.body;

import com.google.gson.Gson;

public abstract class CommandBody {

    public String toJsonString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}
