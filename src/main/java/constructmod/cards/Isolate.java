package constructmod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;

import constructmod.ConstructMod;
import constructmod.patches.AbstractCardEnum;
import constructmod.powers.DoubleNextDamagePower;

public class Isolate extends AbstractCycleCard {
	public static final String ID = ConstructMod.makeID("Isolate");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final String M_UPGRADE_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];
	private static final int COST = 1;
	//private static final int UPGRADE_NEW_COST = 0;
	//private static final int DOUBLE_DMG_TURNS = 1;
	//private static final int M_UPGRADE_DOUBLE_DMG_TURNS = 1;
	private static final int POOL = 1;

	public Isolate() {
		super(ID, NAME, "img/cards/"+ID+".png", COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.CONSTRUCTMOD, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		//this.exhaust = true;
	}
	
	@Override
	public boolean canCycle() {
		int count = 0;
        for (final AbstractMonster mon : AbstractDungeon.getMonsters().monsters) {
            if (!mon.isDeadOrEscaped()) {
                ++count;
            }
        }
		return super.canCycle() && count > 1;
				
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (!upgraded) AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,m,new DoubleNextDamagePower(p,1),1));
		else AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,m,new DoubleDamagePower(p,(this.megaUpgraded?2:1),false),(this.megaUpgraded?2:1)));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Isolate();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			//this.upgradeBaseCost(UPGRADE_NEW_COST);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		} else if (canUpgrade()) {
			this.megaUpgradeName();
			this.rawDescription = M_UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
