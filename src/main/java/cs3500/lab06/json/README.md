# JSON

This package holds all classes used for JSON serialization and deserialization.
Java records are used as intermediate data structures to convert from Strings
in JSON form to usable data. [GuessJson](GuessJson.java),
[HintJSON](HintJson.java), [MessageJson](MessageJson.java), and [WinJson](WinJson.java)
are all records used as intermediate data types. [JsonUtils](JsonUtils.java) is
a utility class used for general methods that deal with JSON serialization/deserialization.

## File Structure

| File                            | Description                                                |
|---------------------------------|------------------------------------------------------------|
| [GuessJson](GuessJson.java)     | Simple record to hold a guess as described in JSON         |
| [HintJson](HintJson.java)       | Simple record to hold a guess hint from the server in JSON |
| [JsonUtils](JsonUtils.java)     | Utility class for general methods that deal with JSON      |
| [MessageJson](MessageJson.java) | Simple record to hold a message from the server            |
| [WinJson](WinJson.java)         | Simple record to hold a win as described in JSON           |