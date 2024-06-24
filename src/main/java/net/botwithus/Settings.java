package net.botwithus;

import net.botwithus.rs3.imgui.ImGui;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.script.ScriptGraphicsContext;

public class Settings extends ScriptGraphicsContext {

    private final DungHole dh;

    public Settings(DungHole dh, ScriptConsole scriptConsole) {
        super(scriptConsole);
        this.dh = dh;
    }

    @Override
    public void drawSettings() {
        if(ImGui.Begin("Dung Hole Settings", 0)) {
            ImGui.Text("Cool down: " + dh.cooldown() + " / 100");
            boolean eat = ImGui.Checkbox("Eat Icecream", dh.isEatIcecream());
            if(eat != dh.isEatIcecream()) {
                dh.setEatIcecream(eat);
            }
        }
        ImGui.End();
    }
}
