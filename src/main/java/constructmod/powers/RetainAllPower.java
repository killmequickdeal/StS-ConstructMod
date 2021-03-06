package constructmod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import constructmod.ConstructMod;
import constructmod.actions.RetainAllCardsAction;
import constructmod.actions.RetainRandomCardAction;

public class RetainAllPower extends AbstractPower {
	public static final String POWER_ID = ConstructMod.makeID("SaveState");
	public static final String NAME = "Save State";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, #yRetain your hand."
	};

	public RetainAllPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = PowerType.BUFF;
		this.isTurnBased = true;
		this.priority = 4;
		this.loadRegion("retain");
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

	@Override
	public void atEndOfRound() {
		if (this.amount == 0) {
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
		}
	}
	
	@Override
	public void atEndOfTurn(final boolean isPlayer) {
		if (!isPlayer) return;
		AbstractDungeon.actionManager.addToBottom(new RetainAllCardsAction());
	}
}