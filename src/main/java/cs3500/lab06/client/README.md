# Client

This package is in charge of connecting to the server and playing the Guess the Number game.
[Driver](Driver.java) establishes a connection with the server and then
instantiates a [ProxyDealer](ProxyDealer.java) to handle communication with
the server. The ProxyDealer also takes a [PlayerController](PlayerController.java)
for use answering the server's requests. The [RandomPlayerController](RandomPlayerController.java)
is a simple implementation of the Player interface used by this Client.

## File Structure

| File                                                  | Description                                                    |
|-------------------------------------------------------|----------------------------------------------------------------|
| [Driver](Driver.java)                                 | Connects the client to the server                              |
| [PlayerController](PlayerController.java)             | The PlayerController interface                                 |
| [ProxyDealer](ProxyDealer.java)                       | Handles Client-Server communication                            |
| [RandomPlayerController](RandomPlayerController.java) | Automated implementation of the PlayerController interface     |
| [ManualPlayerController](ManualPlayerController.java) | User-playable implementation of the PlayerController interface |