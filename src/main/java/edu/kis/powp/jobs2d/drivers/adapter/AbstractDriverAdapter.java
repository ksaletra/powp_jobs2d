package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.powp.jobs2d.AbstractDriver;
import edu.kis.powp.jobs2d.Job2dDriver;

public class AbstractDriverAdapter extends AbstractDriver {
    private Job2dDriver driver;

    public AbstractDriverAdapter(Job2dDriver driver) {
        super(0, 0);
        this.driver = driver;
    }

    @Override
    public void operateTo(int x, int y) {
        driver.setPosition(getX(), getY());
        driver.operateTo(x, y);
    }

    @Override
    public String toString() {
        return "AbstractDriverAdapter wrapping " + driver.toString();
    }
}