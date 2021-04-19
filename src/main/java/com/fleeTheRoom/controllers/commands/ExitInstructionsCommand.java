package com.fleeTheRoom.controllers.commands;

import com.fleeTheRoom.view.Instructions.InstructionsGUI;

public class ExitInstructionsCommand implements Command {
        private final InstructionsGUI gui;

        public ExitInstructionsCommand(InstructionsGUI gui) {
            this.gui = gui;
        }

        @Override
        public void execute() {
            gui.close();
        }
}

