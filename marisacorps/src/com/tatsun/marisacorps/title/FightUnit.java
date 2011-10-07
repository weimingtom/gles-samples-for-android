package com.tatsun.marisacorps.title;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.tatsun.lib.gles.GGLIBinderObject;
import com.tatsun.lib.gles.GGLTexture;
import com.tatsun.lib.gles.GGLVertexCoordsBufferObject;
import com.tatsun.lib.gles.helper.GGLCamera;
import com.tatsun.lib.gles.helper.GGLSpriteAnimater;
import com.tatsun.lib.math.Vector3f;
import com.tatsun.marisacorps.GLResources;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class FightUnit {
	public enum ActionStatus {
		ACTION_STATUS_STARTWAIT,
		ACTION_STATUS_MOVE,
		ACTION_STATUS_MOVEBACK,
		ACTION_STATUS_ATTACK,
		ACTION_STATUS_DEFEATED,
		ACTION_STATUS_WAIT,
	}
	
	GGLCamera camera;
	FightUnit target;
	boolean isDamaged = false;
	boolean isFinished = false;
	boolean isActive = true;

	int patternDir;
	int startDir;
	float unitSize;
	Vector3f position = new Vector3f();
	Vector3f startPosition = new Vector3f();
	Vector3f targetPosition = new Vector3f();
	Vector3f direction = new Vector3f();

	ActionStatus actionStatus;
	int tick;
	
	public FightUnit() {
		
		actionStatus = ActionStatus.ACTION_STATUS_WAIT;
	}
	
	public void init() {
		isDamaged = false;
		isFinished = false;
		isActive = true;
		checkDirection();
	}

	public void checkDirection() {
//		float atan = (float)Math.atan2(position.y, position.x);
//		if(atan < FightConst.pi45i) {
//			if(atan < FightConst.pi135i) {
//				direction = FightConst.DIRECTION_DOWN;
//			} else {
//				direction = FightConst.DIRECTION_RIGHT;
//			}
//		} else if(atan > FightConst.pi45){
//			if(atan > FightConst.pi135) {
//				direction = FightConst.DIRECTION_DOWN;
//			} else {
//				direction = FightConst.DIRECTION_LEFT;
//			}
//			
//		} else {
//			direction = FightConst.DIRECTION_UP;
//		}
		if(direction.x > 0) {
			patternDir = FightConst.DIRECTION_RIGHT;
		} else if(direction.x < 0) {
			patternDir = FightConst.DIRECTION_LEFT;
		}
	}
	
	public void move(float offsetX, float offsetY, float offsetZ) {
		position.offset(offsetX, offsetY, offsetZ);
	}
	
//
//	protected Vector2D getVectorByType(int type) {
//		if(unitData.unitSide == BattleConst.UNIT_SIDE_USER) {
//			return Vector2D.RIGHT;
//		} else {
//			return Vector2D.LEFT;
//		}
//	}
	
//	public void damaged(FightUnit chip) {
//		//if(isDamaged)
//		//	return;
//		
//		float arate = BattleConst.BATTLE_RATE_MATRIX[chip.classData.classType][classData.classType];
//		float drate = BattleConst.BATTLE_RATE_MATRIX[classData.classType][chip.classData.classType];
//		if(GameUtils.getRand(1f) > chip.classData.hitRatio * arate * (1 - classData.avoidRatio * drate)) {
//			//Log.i("marisa_battle", "attack avoid.");
//			return;
//		}
//		float damage = Math.max(1, chip.classData.attackPoint * arate - classData.deffencePoint * drate);
//		//Log.i("marisa_battle", "hit damage:"+damage+" from life:"+unitData.life);
//		unitData.life -= damage;
//		attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//		tick = classData.knockbackTime;
//		if(direction == GameConst.DIRECTION_LEFT) {
//			this.move(classData.knockback, 0);
//		} else {
//			this.move(-classData.knockback, 0);
//		}
//		if(unitData.life < 0) {
//			BattleStatus.mana[(unitData.unitSide+1)%2] += classData.manaPoint;
//			startDeadAction();
//			return;
//		}
//		
//		isDamaged = true;
//	}

//	protected void startDeadAction() {
//		actionStatus = ActionStatus.ACTION_STATUS_DEAD;
//		tick = classData.deadFrame;
//	}
	
	/**
	 * 
	 */
	public void update(int frame) {
		
		if(isDamaged) {
			updateDamaged();
			return;
		}

		switch(actionStatus) {
		case ACTION_STATUS_STARTWAIT:
			updateStartWait();
			break;
		case ACTION_STATUS_WAIT:
			updateWait();
			break;
		case ACTION_STATUS_MOVE:
			updateMove();
			break;
		case ACTION_STATUS_MOVEBACK:
			updateMoveback();
			break;
		case ACTION_STATUS_ATTACK:
			updateAttack();
			break;
		case ACTION_STATUS_DEFEATED:
			updateDefeated();
			break;
		}
	}
	
	protected void updateStartWait() {
		if(tick-- <= 0) {
			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
		}
	}
	
	protected void updateWait() {
	}
	
	protected void updateMove() {
//		Vector2D pos = getPosition();
//		
//		if(target == null || !target.isActive()) {
//			if(target != null) {
//				actionStatus = ActionStatus.ACTION_STATUS_ATTACK;
//				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//				return;
//			}
//		}
//		
//		if(pos.isEquals(targetPosition)) {
//			actionStatus = ActionStatus.ACTION_STATUS_WAIT;
//			return;
//		}
//		
//		workVec_0.set(pos, targetPosition);
//		float dist = workVec_0.length();
//		if(dist < classData.unitSpeed) {
//			actionStatus = ActionStatus.ACTION_STATUS_WAIT;
//		} else {
//			workVec_0.normalize().times(classData.unitSpeed);
//		}
//		this.setDirection(workVec_0);
//		this.move(workVec_0);
	}
	
	protected void updateMoveback() {
//		PointF pos = getPosition();
//		if(pos.y == startPosition.y) {
//			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
//			return;
//		}
//		workVec_0.set(0, startPosition.y - pos.y);
//		float dist = workVec_0.length();
//		if(dist < classData.unitSpeed) {
//			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
//		} else {
//			workVec_0.normalize().times(classData.unitSpeed);
//		}
//		this.setDirection(workVec_0);
//		this.move(workVec_0);
	}
	
	protected void updateAttack() {
		
	}

	protected void updateDamaged() {
		if(tick-- <= 0) {
			isDamaged = false;
		}
	}
	
	protected void updateDefeated() {
		// add to particle
	}


	/**
	 * 
	 * @param gl
	 * @param frameCount
	 */
	public void draw(GL11 gl, int frame) {
		drawStartWait(gl, frame);
		if(true)
			return;
		if(isDamaged) {
			drawDamaged(gl, frame);
			return;
		}
		
		switch(actionStatus) {
		case ACTION_STATUS_STARTWAIT:
			drawStartWait(gl, frame);
			break;
		case ACTION_STATUS_WAIT:
			drawWait(gl, frame);
			break;
		case ACTION_STATUS_MOVE:
			drawMove(gl, frame);
			break;
		case ACTION_STATUS_MOVEBACK:
			drawMoveback(gl, frame);
			break;
		case ACTION_STATUS_ATTACK:
			drawAttack(gl, frame);
			break;
		case ACTION_STATUS_DEFEATED:
			drawDefeated(gl, frame);
			break;
		}
		
	}

	protected void drawStartWait(GL11 gl11, int frame) {
		GGLTexture texture = GLResources.textures[GLResources.TEXTURE_UNITS_01];
		GGLVertexCoordsBufferObject vbo = (GGLVertexCoordsBufferObject)GLResources.vbo[GLResources.VBO_UNIT_01];
		GGLIBinderObject ibo = GLResources.vbo[GLResources.IBO_UNIT_01];
		GGLSpriteAnimater sprite1 = GLResources.patterns[GLResources.PATTERN_UNITS_01 + 2]; 
		GGLSpriteAnimater sprite2 = GLResources.patterns[GLResources.PATTERN_UNITS_01 + 9]; 
		texture.bind(gl11);
		vbo.bind(gl11, sprite1.getCurrentUnit(frame, patternDir));
		gl11.glPushMatrix();
		{
			gl11.glTranslatef(position.x, position.y, position.z);
			camera.applayBillboard(gl11);
			ibo.draw(gl11);
		}
		gl11.glPopMatrix();
		vbo.unbind(gl11);
		texture.unbind(gl11);
	}

	protected void drawWait(GL11 gl, int frameCount) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
	}
	
	protected void drawMove(GL11 gl, int frameCount) {
		
	}
	
	protected void drawMoveback(GL11 gl, int frameCount) {
		
	}
	
	protected void drawAttack(GL11 gl, int frameCount) {
		
	}

	protected void drawDamaged(GL11 gl, int frameCount) {
		
	}
	
	protected void drawDefeated(GL11 gl, int frameCount) {
	}
}

///**
// * 
// * @author ta-koyama
// *
// */
//class BattleChipAsult extends FightUnit {
//	
//	Vector2D weaponVec = new Vector2D();
//	
//	public BattleChipAsult(Context context) {
//		super(context);
//	}
//
//	////////////////////////////////////////////
//	//	update
//	//
//
////	@Override
////	protected void updateStartWait() {
////		if(tick-- <= 0) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////		}
////	}
////	
////	@Override
////	protected void updateMove() {
////		PointF pos = getPosition(); 
////		if(!BattleStatus.FIELD_VALID_AREA.contains(pos.x, pos.y)) {
////			this.isActive = false;
////			return;
////		}
////		if(target == null || !target.isActive()) {
////			target = manager.searchTarget(this, classData.searchRange);
////			if(target != null) {
////				actionStatus = ActionStatus.ACTION_STATUS_ATTACK;
////				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
////				return;
////			}
////		}
////		setDirection(velocity);
////		this.move(velocity);
////	}
//
//	@Override
//	protected void updateAttack() {
//		if(!target.isActive()) {
////			target = manager.searchTarget(this, classData.searchRange);
////			if(target != null) {
//				actionStatus = ActionStatus.ACTION_STATUS_MOVE;
//				return;
////			}
////			attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//		}
//		
//		switch(attackStatus) {
//		case ATTACK_STATUS_RETRIEVE:
//			{
//				//Log.i("marisa_battle", "ATTACK_STATUS_RETRIEVE");
//				workVec_0.set(getPosition(), target.getPosition());
//				float dist = workVec_0.length();
//				if(dist < classData.attackRange) {
//					//Log.i("marisa_battle", "!!!attack!!!");
//					attackStatus = AttackStatus.ATTACK_STATUS_START;
//					return;
//				} else {
//					workVec_0.normalize().times(classData.unitSpeed);
//				}
//				this.setDirection(workVec_0);
//				this.move(workVec_0);
//			}
//			break;
//		case ATTACK_STATUS_START:
//			{
//				setDirection(workVec_0.set(getPosition(), target.getPosition()));
//				attackStatus = AttackStatus.ATTACK_STATUS_ACTION;
//				chipData.weaponDegree = 60;
//			}
//			break;
//		case ATTACK_STATUS_ACTION:
//			{
//				this.setDirection(workVec_0.set(getPosition(), target.getPosition()));
//				chipData.weaponDegree -= 15;
//				if(chipData.weaponDegree <= -60) {
//					target.damaged(this);
//					attackStatus = AttackStatus.ATTACK_STATUS_WAIT;
//					tick = classData.attackWait;
//					return;
//				}		
//				//Log.i("marisa_battle", "ATTACK_STATUS_ACTION");
//			}
//			break;
//		case ATTACK_STATUS_WAIT:
//			//Log.i("marisa_battle", "ATTACK_STATUS_WAIT");
//			if(tick-- <= 0) {
//				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//			}
//			break;
//		}
//	
//	}
//	
//	@Override
//	protected void updateDefence() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void updateExpired() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	////////////////////////////////////////////
//	//	draw
//	//
//	
//	@Override
//	protected void drawStartWait(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//	
//	@Override
//	protected void drawMove(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
////	@Override
////	protected void drawMoveback(Canvas canvas, int frameCount, float offsetX, float offsetY) {
////		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
////		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
////	}
//
//	@Override
//	protected void drawAttack(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//		switch(attackStatus) {
//		case ATTACK_STATUS_ACTION:
//			weaponVec.set(getPosition());weaponVec.dec(offsetX, offsetY);
//			if(direction == GameConst.DIRECTION_LEFT) {
//				weaponVec.offset(-GameConst.UNIT_WIDTH2, 0);
//				classData.weaponSprite[direction].draw(canvas, frameCount, weaponVec, chipData.weaponDegree, workVec_0, null);
//			} else {
//				weaponVec.offset(GameConst.UNIT_WIDTH2, 0);
//				classData.weaponSprite[direction].draw(canvas, frameCount, weaponVec, -chipData.weaponDegree, workVec_0, null);
//			}
//			break;
//		}
//	}
//
//	@Override
//	protected void drawDefence(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawDamaged(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawExpired(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	public void drawEffect(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		//
//	}
//
//}
//
///**
// * 
// *
// */
//class BattleChipCommando extends FightUnit {
//	Vector2D weaponVec = new Vector2D();
//	
//	public BattleChipCommando(Context context) {
//		super(context);
//	}
//
//	////////////////////////////////////////////
//	//	update
//	//
//
//	
////	@Override
////	protected void updateMove() {
////		PointF pos = getPosition(); 
////		if(!BattleStatus.FIELD_VALID_AREA.contains(pos.x, pos.y)) {
////			this.isActive = false;
////			return;
////		}
////		if(target == null || !target.isActive()) {
////			target = manager.searchTarget(this, classData.searchRange);
////			if(target != null) {
////				actionStatus = ActionStatus.ACTION_STATUS_ATTACK;
////				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
////				return;
////			}
////		}
////		setDirection(velocity);
////		this.move(velocity);
////	}
//
////	@Override
////	protected void updateMoveback() {
////		PointF pos = getPosition();
////		if(pos.y == startPosition.y) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////			return;
////		}
////		workVec_0.set(0, pos.y - startPosition.y);
////		float dist = workVec_0.length();
////		
////		if(dist < classData.unitSpeed) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////		} else {
////			workVec_0.normalize().times(classData.unitSpeed);
////		}
////		this.setDirection(workVec_0);
////		this.move(workVec_0);
////	}
//
//	@Override
//	protected void updateAttack() {
//		if(!target.isActive()) {
//			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
//			return;
//		}
//		
//		switch(attackStatus) {
//		case ATTACK_STATUS_RETRIEVE:
//			{
//				//Log.i("marisa_battle", "ATTACK_STATUS_RETRIEVE");
//				workVec_0.set(getPosition(), target.getPosition());
//				float dist = workVec_0.length();
//				if(dist < classData.attackRange) {
//					//Log.i("marisa_battle", "!!!attack!!!");
//					attackStatus = AttackStatus.ATTACK_STATUS_START;
//					return;
//				} else {
//					workVec_0.normalize().times(classData.unitSpeed);
//				}
//				this.setDirection(workVec_0);
//				this.move(workVec_0);
//			}
//			break;
//		case ATTACK_STATUS_START:
//			{
//				setDirection(workVec_0.set(getPosition(), target.getPosition()));
//				attackStatus = AttackStatus.ATTACK_STATUS_ACTION;
//				weaponVec.set(getPosition());
//			}
//			break;
//		case ATTACK_STATUS_ACTION:
//			{
//				workVec_0.set(weaponVec, target.getPosition());
//				this.setDirection(workVec_0);
//				
//				float hitLength = workVec_0.length();
//				if(hitLength <= GameConst.UNIT_WIDTH2) {
//					target.damaged(this);
//					attackStatus = AttackStatus.ATTACK_STATUS_WAIT;
//					tick = classData.attackWait;
//					return;
//				}
//				workVec_0.normalize().times(classData.weaponSpeed);
//				weaponVec.offset(workVec_0);
//				//Log.i("marisa_battle", "ATTACK_STATUS_ACTION");
//			}
//			break;
//		case ATTACK_STATUS_WAIT:
//			//Log.i("marisa_battle", "ATTACK_STATUS_WAIT");
//			if(tick-- <= 0) {
//				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//			}
//			break;
//		}
//	
//	}
//	
//	@Override
//	protected void updateDefence() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void updateExpired() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	////////////////////////////////////////////
//	//	draw
//	//
//	
//	@Override
//	protected void drawStartWait(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//	
//	@Override
//	protected void drawMove(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
////	@Override
////	protected void drawMoveback(Canvas canvas, int frameCount, float offsetX, float offsetY) {
////		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
////		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
////	}
//
//	@Override
//	protected void drawAttack(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//		switch(attackStatus) {
//		case ATTACK_STATUS_ACTION:
//			workVec_0.set(weaponVec);workVec_0.dec(offsetX, offsetY);
//			classData.weaponSprite[direction].draw(canvas, frameCount, workVec_0, null);
//			break;
//		}
//	}
//
//	@Override
//	protected void drawDefence(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawDamaged(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawExpired(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	public void drawEffect(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		//
//	}
//
//}
//
//
///**
// * 
// *
// */
//class BattleChipShoot extends FightUnit {
//	Vector2D weaponVec = new Vector2D();
//	
//	public BattleChipShoot(Context context) {
//		super(context);
//	}
//
//	////////////////////////////////////////////
//	//	update
//	//
//
////	@Override
////	protected void updateStartWait() {
////		if(tick-- <= 0) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////		}
////	}
////	
////	@Override
////	protected void updateMove() {
////		PointF pos = getPosition(); 
////		if(!BattleStatus.FIELD_VALID_AREA.contains(pos.x, pos.y)) {
////			this.isActive = false;
////			return;
////		}
////		if(target == null || !target.isActive()) {
////			target = manager.searchTarget(this, classData.searchRange);
////			if(target != null) {
////				actionStatus = ActionStatus.ACTION_STATUS_ATTACK;
////				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
////				return;
////			}
////		}
////		setDirection(velocity);
////		this.move(velocity);
////	}
//
////	@Override
////	protected void updateMoveback() {
////		PointF pos = getPosition();
////		if(pos.y == startPosition.y) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////			return;
////		}
////		workVec_0.set(0, pos.y - startPosition.y);
////		float dist = workVec_0.length();
////		if(dist >= classData.unitSpeed) {
////			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
////		} else {
////			workVec_0.normalize().times(classData.unitSpeed);
////		}
////		this.setDirection(workVec_0);
////		this.move(workVec_0);
////	}
//
//	@Override
//	protected void updateAttack() {
//		if(!target.isActive()) {
//			actionStatus = ActionStatus.ACTION_STATUS_MOVE;
//			return;
//		}
//		
//		switch(attackStatus) {
//		case ATTACK_STATUS_RETRIEVE:
//			{
//				//Log.i("marisa_battle", "ATTACK_STATUS_RETRIEVE");
//				workVec_0.set(getPosition(), target.getPosition());
//				float dist = workVec_0.length();
//				if(dist < classData.attackRange) {
//					//Log.i("marisa_battle", "!!!attack!!!");
//					attackStatus = AttackStatus.ATTACK_STATUS_START;
//					return;
//				} else {
//					workVec_0.normalize().times(classData.unitSpeed);
//				}
//				this.setDirection(workVec_0);
//				this.move(workVec_0);
//			}
//			break;
//		case ATTACK_STATUS_START:
//			{
//				setDirection(workVec_0.set(getPosition(), target.getPosition()));
//				attackStatus = AttackStatus.ATTACK_STATUS_ACTION;
//				weaponVec.set(getPosition());
//			}
//			break;
//		case ATTACK_STATUS_ACTION:
//			{
//				workVec_0.set(weaponVec, target.getPosition());
//				this.setDirection(workVec_0);
//				float hitLength = workVec_0.length();
//				if(hitLength <= GameConst.UNIT_WIDTH2) {
//					target.damaged(this);
//					attackStatus = AttackStatus.ATTACK_STATUS_WAIT;
//					tick = classData.attackWait;
//					return;
//				}
//				workVec_0.normalize().times(classData.weaponSpeed);
//				weaponVec.offset(workVec_0);
//				//Log.i("marisa_battle", "ATTACK_STATUS_ACTION");
//			}
//			break;
//		case ATTACK_STATUS_WAIT:
//			//Log.i("marisa_battle", "ATTACK_STATUS_WAIT");
//			if(tick-- <= 0) {
//				attackStatus = AttackStatus.ATTACK_STATUS_RETRIEVE;
//			}
//			break;
//		}
//	
//	}
//	
//	@Override
//	protected void updateDefence() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	protected void updateExpired() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	////////////////////////////////////////////
//	//	draw
//	//
//	
//	@Override
//	protected void drawStartWait(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//	
//	@Override
//	protected void drawMove(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
////	@Override
////	protected void drawMoveback(Canvas canvas, int frameCount, float offsetX, float offsetY) {
////		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
////		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
////	}
//
//	@Override
//	protected void drawAttack(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//		switch(attackStatus) {
//		case ATTACK_STATUS_ACTION:
//			workVec_0.set(weaponVec);workVec_0.dec(offsetX, offsetY);
//			classData.weaponSprite[direction].draw(canvas, frameCount, workVec_0, null);
//			break;
//		}
//	}
//
//	@Override
//	protected void drawDefence(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawDamaged(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	protected void drawExpired(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		classData.chipSprite.draw(canvas, frameCount, workVec_0, null, direction);
//	}
//
//	@Override
//	public void drawEffect(Canvas canvas, int frameCount, float offsetX, float offsetY) {
//		workVec_0.set(getPosition());workVec_0.dec(offsetX, offsetY);
//		//
//	}
//
//}

