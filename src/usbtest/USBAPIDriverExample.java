package usbtest;

import jssc.*;

public class USBAPIDriverExample {
	static SerialPort serialPort = new SerialPort("/dev/cu.usbmodem14241");
	
	public static void main(String[] args) throws SerialPortException {
		
		serialPort.openPort();//Open serial port
		serialPort.setParams(9600, 8, 1, 0);//Set params.
		
	    serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);

	    serialPort.writeString("CONTROL");
	}

	private static class PortReader implements SerialPortEventListener {

		@Override
    	    public void serialEvent(SerialPortEvent event) {
    	        if(event.isRXCHAR() && event.getEventValue() > 0) {
    	        	if (event.getEventValue() > 2) {//Check bytes count in the input buffer
    	        		try {
                            byte buffer[] = serialPort.readBytes(2);

                            
                            String command = new String(buffer);
                            if(command.equals("bp") ) {
                            	System.out.println("Button Press");
                            	serialPort.readBytes(1); // Burn the space
                            	String button = new String(serialPort.readBytes(1));
                            	System.out.println("Button #:"+ button);
                            } else if(command.equals("ce") ) {
                            	System.out.println("Cube Event");
                            	serialPort.readBytes(1); // Burn the space
                            	String button = new String(serialPort.readBytes(1));
                            	System.out.println("Cube status (not implemented):"+ button);
                            } 
                        } catch (SerialPortException ex) {
                            System.out.println(ex);
                        }	
    	        	}
    	        }
    	    }
	}
	
}

