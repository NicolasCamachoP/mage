/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package org.mage.test.AI.basic;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseAI;

/**
 *
 * @author LevelX2
 */
public class CastCreaturesTest extends CardTestPlayerBaseAI {

    /**
     * Tests that the creature is cast if enough mana is available
     */
    @Test
    public void testSimpleCast() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * This test fails sometimes, probably because two plains are tapped for the
     * first creature
     */
    @Test
    // TODO: find out why sometimes Produces error probably because of wrong mana usage of the AI - Not solved yet
    public void testSimpleCast2() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 2);
    }

    @Test
    public void testSimpleCast3() {
        // Affinity for artifacts (This spell costs less to cast for each artifact you control.)
        addCard(Zone.HAND, playerA, "Myr Enforcer");
        // {T}: Add to your mana pool.
        // {T}, {1}, Sacrifice Mind Stone: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Myr Enforcer", 1);
    }

    @Test
    public void testSimpleCast4() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.HAND, playerA, "Fireshrieker");
        addCard(Zone.HAND, playerA, "Blazing Specter"); // {2}{R}{B}

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Fireshrieker", 0);
        assertPermanentCount(playerA, "Blazing Specter", 1);
    }

    @Test
    public void testSimpleCast5() {
        addCard(Zone.HAND, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 3);
        addCard(Zone.HAND, playerA, "Soul Warden");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Soul Warden", 1);
    }

    @Test
    public void testSimpleCast6() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Pillarfield Ox", 1);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void testCast4Creature() {
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Mountain", 1);
        addCard(Zone.LIBRARY, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Loyal Sentry");       // {W}     1/1
        addCard(Zone.HAND, playerA, "Silvercoat Lion");    // {1}{W}  2/2
        addCard(Zone.HAND, playerA, "Rootwater Commando"); // {2}{U}  2/2
        addCard(Zone.HAND, playerA, "Bog Wraith");         // {3}{B}  3/3

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Swamp", 1);

        // assertLife(playerB, 11);  // 1 + 1+2 +  1+2+2 =
        assertPermanentCount(playerA, "Loyal Sentry", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Rootwater Commando", 1);
        assertPermanentCount(playerA, "Bog Wraith", 1);

    }

    @Test
    public void testCast4Creature2() {
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Swamp", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 1);

        addCard(Zone.HAND, playerA, "Island", 1);
        addCard(Zone.HAND, playerA, "Plains");
        skipInitShuffling();

        addCard(Zone.HAND, playerA, "Loyal Sentry");       // {W}     1/1
        addCard(Zone.HAND, playerA, "Steadfast Guard");    // {W}{W}  2/2
        addCard(Zone.HAND, playerA, "Rootwater Commando"); // {2}{U}  2/2
        addCard(Zone.HAND, playerA, "Bog Wraith");         // {3}{B}  3/3

        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Plains", 2);
        assertPermanentCount(playerA, "Island", 1);
        assertPermanentCount(playerA, "Swamp", 1);

        // assertLife(playerB, 11);  // 1 + 1+2 +  1+2+2 =
        assertPermanentCount(playerA, "Loyal Sentry", 1);
        assertPermanentCount(playerA, "Steadfast Guard", 1);
        assertPermanentCount(playerA, "Rootwater Commando", 1);
        assertPermanentCount(playerA, "Bog Wraith", 1);

    }

    /**
     * Tests that the creature is cast if enough mana is available.
     *
     * Once Ammit Eternal is cast against a computer AI opponent, the AI just
     * decides to sit there and only play basic lands. I've sat there and decked
     * it because it just plays lands. It's like it views giving the Ammit
     * Eternal -1/-1 counters as a good thing for me, so it never casts any
     * spells once Ammit Eternal hits the battlefield.
     */
    @Test
    public void testSimpleCastWithAmmitEternal() {
        addCard(Zone.HAND, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Plains", 2);
        addCard(Zone.LIBRARY, playerA, "Plains", 2);
        skipInitShuffling();

        // Afflict 3 (Whenever this creature becomes blocked, defending player loses 3 life.)
        // Whenever an opponent casts a spell, put a -1/-1 counter on Ammit Eternal.
        // Whenever Ammit Eternal deals combat damage to a player, remove all -1/-1 counters from it.
        addCard(Zone.HAND, playerB, "Ammit Eternal"); // Creature {2}{B}  5/5
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Ammit Eternal");
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Ammit Eternal", 1);

        assertPermanentCount(playerA, "Plains", 2);
        assertHandCount(playerA, "Plains", 1);
        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, 3);

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertPowerToughness(playerB, "Ammit Eternal", 4, 4);
    }

}
