package edu.kis.powp.command;

import edu.kis.powp.jobs2d.Job2dDriver;

public class CommandRecorder implements Job2dDriver {
    private ComplexCommand recordedCommand = new ComplexCommand();

    @Override
    public void setPosition(int x, int y) {
        recordedCommand.addCommand(new SetPositionCommand(x, y));
    }

    @Override
    public void operateTo(int x, int y) {
        recordedCommand.addCommand(new OperateToCommand(x, y));
    }

    public ComplexCommand getRecordedCommand() {
        return recordedCommand;
    }

    public void clearRecording() {
        recordedCommand = new ComplexCommand();
    }

    @Override
    public String toString() {
        return "CommandRecorder";
    }
}