package br.unifei.imc.test;


import br.unifei.imc.bin.cli.Command;
import br.unifei.imc.utils.factory.Factory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.unifei.imc.bin.cli.CommandsAvailable;

public class TestMain {
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }
  @Test
  public void test()
      throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    Command command = Factory.getCommand(CommandsAvailable.Help);

    Assert.assertNotNull(command);

    Assert.assertEquals("br.unifei.imc.bin.cli.help.Help", command.getClass().getName());

    command.run();

    Assert.assertEquals("Help for the CLI", outContent.toString().trim());
  }
}
