package edu.kis.powp.command;

public class FigureCommandFactory {

    public static ComplexCommand createRectangle(int x, int y, int width, int height){
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(x,y));
        command.addCommand(new OperateToCommand(x+ width,y));
        command.addCommand(new OperateToCommand(x + width, y + height));
        command.addCommand(new OperateToCommand(x, y + height));
        command.addCommand(new OperateToCommand(x,y));
        return command;

    }
    public static ComplexCommand createTriangle(int x, int y, int x2, int y2, int x3, int y3){
        ComplexCommand command = new ComplexCommand();
        command.addCommand(new SetPositionCommand(x, y));
        command.addCommand(new OperateToCommand(x2, y2));
        command.addCommand(new OperateToCommand(x3, y3));
        command.addCommand(new OperateToCommand(x, y));
        return command;
    }
    public static ComplexCommand createCircle(int cx, int cy, int radius, int segments){
        ComplexCommand command = new ComplexCommand();
        double angleStep = 2 * Math.PI / segments;

        int startX = cx + radius;
        int startY = cy;
        command.addCommand(new SetPositionCommand(startX, startY));

        for(int i = 1; i <= segments; i++){
            int px = cx + (int)(radius * Math.cos(i * angleStep));
            int py = cy + (int)(radius * Math.sin(i * angleStep));
            command.addCommand(new OperateToCommand(px, py));
        }
        return command;
    }
}
