package net.minecraft.server;

import javax.annotation.Nullable;

public class EntitySquid extends EntityWaterAnimal {
    public float a;
    public float b;
    public float c;
    public float bC;
    public float bD;
    public float bE;
    public float bF;
    public float bG;
    private float bH;
    private float bI;
    private float bJ;
    private float bK;
    private float bL;
    private float bM;

    public EntitySquid(World world) {
        super(EntityTypes.SQUID, world);
        this.setSize(0.8F, 0.8F);
        this.random.setSeed((long)(1 + this.getId()));
        this.bI = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
    }

    protected void n() {
        this.goalSelector.a(0, new EntitySquid.PathfinderGoalSquid(this));
        this.goalSelector.a(1, new EntitySquid.a());
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
    }

    public float getHeadHeight() {
        return this.length * 0.5F;
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_SQUID_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_SQUID_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_SQUID_DEATH;
    }

    protected float cD() {
        return 0.4F;
    }

    protected boolean playStepSound() {
        return false;
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.ar;
    }

    public void k() {
        super.k();
        this.b = this.a;
        this.bC = this.c;
        this.bE = this.bD;
        this.bG = this.bF;
        this.bD += this.bI;
        if ((double)this.bD > (Math.PI * 2D)) {
            if (this.world.isClientSide) {
                this.bD = ((float)Math.PI * 2F);
            } else {
                this.bD = (float)((double)this.bD - (Math.PI * 2D));
                if (this.random.nextInt(10) == 0) {
                    this.bI = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.broadcastEntityEffect(this, (byte)19);
            }
        }

        if (this.aq()) {
            if (this.bD < (float)Math.PI) {
                float f = this.bD / (float)Math.PI;
                this.bF = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
                if ((double)f > 0.75D) {
                    this.bH = 1.0F;
                    this.bJ = 1.0F;
                } else {
                    this.bJ *= 0.8F;
                }
            } else {
                this.bF = 0.0F;
                this.bH *= 0.9F;
                this.bJ *= 0.99F;
            }

            if (!this.world.isClientSide) {
                this.motX = (double)(this.bK * this.bH);
                this.motY = (double)(this.bL * this.bH);
                this.motZ = (double)(this.bM * this.bH);
            }

            float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.aQ += (-((float)MathHelper.c(this.motX, this.motZ)) * (180F / (float)Math.PI) - this.aQ) * 0.1F;
            this.yaw = this.aQ;
            this.c = (float)((double)this.c + Math.PI * (double)this.bJ * 1.5D);
            this.a += (-((float)MathHelper.c((double)f1, this.motY)) * (180F / (float)Math.PI) - this.a) * 0.1F;
        } else {
            this.bF = MathHelper.e(MathHelper.sin(this.bD)) * (float)Math.PI * 0.25F;
            if (!this.world.isClientSide) {
                this.motX = 0.0D;
                this.motZ = 0.0D;
                if (this.hasEffect(MobEffects.LEVITATION)) {
                    this.motY += 0.05D * (double)(this.getEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motY;
                } else if (!this.isNoGravity()) {
                    this.motY -= 0.08D;
                }

                this.motY *= (double)0.98F;
            }

            this.a = (float)((double)this.a + (double)(-90.0F - this.a) * 0.02D);
        }

    }

    public boolean damageEntity(DamageSource damagesource, float f) {
        if (super.damageEntity(damagesource, f) && this.getLastDamager() != null) {
            this.dy();
            return true;
        } else {
            return false;
        }
    }

    private Vec3D b(Vec3D vec3d) {
        Vec3D vec3d1 = vec3d.a(this.b * ((float)Math.PI / 180F));
        vec3d1 = vec3d1.b(-this.aR * ((float)Math.PI / 180F));
        return vec3d1;
    }

    private void dy() {
        this.a(SoundEffects.ENTITY_SQUID_SQUIRT, this.cD(), this.cE());
        Vec3D vec3d = this.b(new Vec3D(0.0D, -1.0D, 0.0D)).add(this.locX, this.locY, this.locZ);

        for(int i = 0; i < 30; ++i) {
            Vec3D vec3d1 = this.b(new Vec3D((double)this.random.nextFloat() * 0.6D - 0.3D, -1.0D, (double)this.random.nextFloat() * 0.6D - 0.3D));
            Vec3D vec3d2 = vec3d1.a(0.3D + (double)(this.random.nextFloat() * 2.0F));
            ((WorldServer)this.world).a(Particles.V, vec3d.x, vec3d.y + 0.5D, vec3d.z, 0, vec3d2.x, vec3d2.y, vec3d2.z, (double)0.1F);
        }

    }

    public void a(float var1, float var2, float var3) {
        this.move(EnumMoveType.SELF, this.motX, this.motY, this.motZ);
    }

    public boolean a(GeneratorAccess generatoraccess, boolean var2) {
        return this.locY > 45.0D && this.locY < (double)generatoraccess.getSeaLevel();
    }

    public void c(float f, float f1, float f2) {
        this.bK = f;
        this.bL = f1;
        this.bM = f2;
    }

    public boolean l() {
        return this.bK != 0.0F || this.bL != 0.0F || this.bM != 0.0F;
    }

    class PathfinderGoalSquid extends PathfinderGoal {
        private final EntitySquid b;

        public PathfinderGoalSquid(EntitySquid entitysquid1) {
            this.b = entitysquid1;
        }

        public boolean a() {
            return true;
        }

        public void e() {
            int i = this.b.cj();
            if (i > 100) {
                this.b.c(0.0F, 0.0F, 0.0F);
            } else if (this.b.getRandom().nextInt(50) == 0 || !this.b.inWater || !this.b.l()) {
                float f = this.b.getRandom().nextFloat() * ((float)Math.PI * 2F);
                float f1 = MathHelper.cos(f) * 0.2F;
                float f2 = -0.1F + this.b.getRandom().nextFloat() * 0.2F;
                float f3 = MathHelper.sin(f) * 0.2F;
                this.b.c(f1, f2, f3);
            }

        }
    }

    class a extends PathfinderGoal {
        private int b;

        private a() {
        }

        public boolean a() {
            EntityLiving entityliving = EntitySquid.this.getLastDamager();
            if (EntitySquid.this.isInWater() && entityliving != null) {
                return EntitySquid.this.h(entityliving) < 100.0D;
            } else {
                return false;
            }
        }

        public void c() {
            this.b = 0;
        }

        public void e() {
            ++this.b;
            EntityLiving entityliving = EntitySquid.this.getLastDamager();
            if (entityliving != null) {
                Vec3D vec3d = new Vec3D(EntitySquid.this.locX - entityliving.locX, EntitySquid.this.locY - entityliving.locY, EntitySquid.this.locZ - entityliving.locZ);
                IBlockData iblockdata = EntitySquid.this.world.getType(new BlockPosition(EntitySquid.this.locX + vec3d.x, EntitySquid.this.locY + vec3d.y, EntitySquid.this.locZ + vec3d.z));
                Fluid fluid = EntitySquid.this.world.b(new BlockPosition(EntitySquid.this.locX + vec3d.x, EntitySquid.this.locY + vec3d.y, EntitySquid.this.locZ + vec3d.z));
                if (fluid.a(TagsFluid.WATER) || iblockdata.isAir()) {
                    double d0 = vec3d.b();
                    if (d0 > 0.0D) {
                        vec3d.a();
                        float f = 3.0F;
                        if (d0 > 5.0D) {
                            f = (float)((double)f - (d0 - 5.0D) / 5.0D);
                        }

                        if (f > 0.0F) {
                            vec3d = vec3d.a((double)f);
                        }
                    }

                    if (iblockdata.isAir()) {
                        vec3d = vec3d.a(0.0D, vec3d.y, 0.0D);
                    }

                    EntitySquid.this.c((float)vec3d.x / 20.0F, (float)vec3d.y / 20.0F, (float)vec3d.z / 20.0F);
                }

                if (this.b % 10 == 5) {
                    EntitySquid.this.world.addParticle(Particles.e, EntitySquid.this.locX, EntitySquid.this.locY, EntitySquid.this.locZ, 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }
}