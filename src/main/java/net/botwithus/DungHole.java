package net.botwithus;

import com.google.common.flogger.backend.Platform;
import net.botwithus.internal.scripts.ScriptDefinition;
import net.botwithus.rs3.game.Coordinate;
import net.botwithus.rs3.game.login.LoginManager;
import net.botwithus.rs3.game.minimenu.actions.ObjectAction;
import net.botwithus.rs3.game.movement.Movement;
import net.botwithus.rs3.game.movement.NavPath;
import net.botwithus.rs3.game.movement.TraverseEvent;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.scene.entities.characters.player.LocalPlayer;
import net.botwithus.rs3.game.scene.entities.object.SceneObject;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.LoopingScript;
import net.botwithus.rs3.script.config.ScriptConfig;

public class DungHole extends LoopingScript {

    private boolean eatIcecream = false;

    public DungHole(String s, ScriptConfig scriptConfig, ScriptDefinition scriptDefinition) {
        super(s, scriptConfig, scriptDefinition);
    }

    private final SceneObjectQuery holes = SceneObjectQuery.newQuery().name("Dungeoneering hole").mark();

    private final Coordinate DUNG_HOLE = new Coordinate(3169, 3248, 0);

    //[Original-Blocked]: DoAction(WALK, 0, 3169, 3248) : 119b20 ffd882a699b56450


    @Override
    public boolean initialize() {
        this.sgc = new Settings(this, getConsole());
        boolean isActive = super.initialize();

        if (configuration.containsKey("eatIcecream")) {
            eatIcecream = Boolean.parseBoolean(configuration.getProperty("eatIcecream"));
        }

        return isActive;
    }

    @Override
    public void onLoop() {
        LocalPlayer self = LocalPlayer.LOCAL_PLAYER;
        if (self.getAnimationId() != -1) {
            return;
        }
        if(cooldown() == 100 && !eatIcecream) {
            return;
        } else if(cooldown() == 100 && !eatIcecream()) {
            return;
        }

        //check distance
        if (self.distanceTo(DUNG_HOLE) > 15) {
            if (Movement.traverse(NavPath.resolve(DUNG_HOLE)) == TraverseEvent.State.FINISHED) {
                delayUntil(5000, () -> self.distanceTo(DUNG_HOLE) < 15);
            }
        }

        doDungHole();
    }

    private void doDungHole() {
        SceneObject hole = holes.results().nearest();
        if (hole != null && hole.interact(ObjectAction.OBJECT1)) {
            delayUntil(60000, () -> LocalPlayer.LOCAL_PLAYER.getAnimationId() != -1);
        }
    }

    public boolean eatIcecream() {
        if(!Backpack.contains(35049)) {
            return false;
        }
        if(Backpack.interact(35049, "Eat")) {
            delayUntil(5000, () -> cooldown() < 20);
            return cooldown() < 20;
        }
        return false;
    }

    public int cooldown() {
        return (VarManager.getVarbitValue(28441) * 100 / 1500);
    }

    public synchronized void setEatIcecream(boolean eatIcecream) {
        this.eatIcecream = eatIcecream;
        configuration.addProperty("eatIcecream", String.valueOf(eatIcecream));
        configuration.save();
    }

    public synchronized boolean isEatIcecream() {
        return eatIcecream;
    }
}
