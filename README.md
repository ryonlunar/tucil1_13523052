# IQ Puzzler Solver with Brute Force Algorithm

![Image](https://github.com/ryonlunar/tucil1_13523052/blob/main/test/solution/assets.png)

This program create to fulfill algorithm strategy subject. This program use Brute Force with Bactracking to find solution in this Puzzle. Goal of this Puzzle is to fit all the Block into Board with all area covered with Block.

## Installation

To start this project, please clone this repository by running the command below in your directory.

```bash
git clone https://github.com/ryonlunar/tucil1_13523052
cd tucil1_13523052
```

## Requirements
- Java 21 or later
- JavaFX 21

It's quite complicated to setup JavaFX if you use VSCode. I suggest you to install IntelliJIDEA and JavaFX. Put JavaFX jar to module in java. If there's any problem to setup this, you can go to CLI branch to run CLI version.


But if you use IntellliJ, you can follow this:

```python
cd src/gui
```
And then run MainGUI.java

OR you can run the file use class with this command:
```bash
java --module-path "D:/Java/openjfx-21.0.6_windows-x64_bin-sdk/javafx-sdk-21.0.6/lib" --add-modules javafx.controls,javafx.fxml -cp "out/production/tucil1" gui.MainGUI
```
Adjust yout openjavafx path into this command, make sure you in root dir.

## Contributing

Pull requests are welcome. For major changes, please open an issue first
to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)