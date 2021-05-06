package com.zozonteq.strictplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EventListener implements Listener {
    @EventHandler
    public void onGetDamage(EntityDamageByEntityEvent e){
        String reason = null;
        if(e.getDamager().getType()== EntityType.PLAYER){
            Player playerDamager =(Player) e.getDamager();
            if(e.getEntity() instanceof Animals){
                 reason = RandomMessage(
                         new String[]{"動物愛護法に違反した", "動物を大事にしなかった","動物に嫌われた","非人道的行為を行った","動物に失礼なことをした"});
                 playerDamager.kickPlayer(reason);
            }
            else if(e.getEntity().getType()==EntityType.PLAYER){
                String name = ((Player) e.getEntity()).getDisplayName();
                reason = RandomMessage(new String[]{name+"に喧嘩を売った",name+"をひっぱたいた",name+"に平手打ちにした"
                        ,name+"の急所を蹴っ飛ばした",name+"にセクハラをした","刑法208条（暴行罪）に違反した"}
                );
                playerDamager.kickPlayer(reason);
            }
        }
        if(reason!=null){
            Bukkit.broadcastMessage(((Player) e.getDamager()).getDisplayName()+"は"+reason+"ので処罰された。");
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        Block block = e.getBlock();
        String reason = null;
        if(block.getType() == Material.TNT){
            reason = RandomMessage(new String[]{"自爆を試みた","テロを起こそうとした","荒そうとした","爆発物取締罰則に違反した"});
            e.getPlayer().kickPlayer(reason);
        }
        if(reason!=null){
            Bukkit.broadcastMessage(((Player) e.getPlayer()).getDisplayName()+"は"+reason+"ので処罰された。");
        }
    }
    @EventHandler
    public void onPlaceLiquid(PlayerBucketEvent e){
        String reason = null;
        if(e.getBucket()==Material.LAVA){
            reason = RandomMessage(new String[]{"森林火災を発生させようとしている","地球温暖化を悪化させようとした","環境破壊に貢献した","焼き畑農業をした","マグマをぶちまけた"});
            e.getPlayer().kickPlayer(reason);
        }
        else if(e.getBucket()==Material.WATER){
            reason = RandomMessage(new String[]{"合法的にエンダーマンに攻撃しようとした","エンダーマンが悲しんだ","水をぶちまけた","窒息死させようとした"});
            e.getPlayer().kickPlayer(reason);
        }
        if(reason!=null){
            Bukkit.broadcastMessage(((Player) e.getPlayer()).getDisplayName()+"は"+reason+"ので処罰された。");
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e ){
        String reason = null;
        if(e.getBlock().getType()==Material.ACACIA_LOG ||e.getBlock().getType()==Material.BIRCH_LOG||e.getBlock().getType()==Material.OAK_LOG||e.getBlock().getType()==Material.DARK_OAK_LOG||
                e.getBlock().getType()==Material.JUNGLE_LOG||e.getBlock().getType()==Material.SPRUCE_LOG){
            reason = RandomMessage(new String[]{"森林破壊に貢献した","木を悲しませた"});
        }
        if(reason!=null){
            Bukkit.broadcastMessage(e.getPlayer().getDisplayName()+"は"+reason+"ので処罰された。");
        }
    }
    @EventHandler
    public void onShift(PlayerToggleSneakEvent e){
        String reason = null;
        if(e.isSneaking()){
            List<Entity> entities= e.getPlayer().getNearbyEntities(e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getZ())
                    .stream()
                    .filter(entity -> ((Player)entity).canSee(e.getPlayer()))
                    .filter(entity -> !e.getPlayer().canSee(((Player) entity).getPlayer()))
                    .collect(Collectors.toList());
            if(!entities.isEmpty()){
                String tg = ((Player) entities.get(0)).getDisplayName();
                reason = RandomMessage(new String[]{tg+"にお尻を突き出した",tg+"にお尻を貸した",tg+"にお尻を露出した"});
            }
        }
        if(reason!=null){
            Bukkit.broadcastMessage(e.getPlayer().getDisplayName()+"は"+reason+"ので処罰された。");
        }
    }
    @EventHandler
    public void onMove(org.bukkit.event.player.PlayerMoveEvent e){
        double distance = 5;
        String reason = null;
        if(e.getPlayer().isSprinting()){
            List<Entity> sleepingPlayersList = e.getPlayer().getNearbyEntities(e.getPlayer().getLocation().getX(),e.getPlayer().getLocation().getY(),e.getPlayer().getLocation().getZ()).stream()
                    .filter(entity -> entity.getType()==EntityType.PLAYER)
                    .filter(entity -> entity.getLocation().distance(e.getPlayer().getLocation())<=distance)
                    .filter(entity -> ((Player) entity).getPlayer().isSleeping())
                    .collect(Collectors.toList());
            if(!sleepingPlayersList.isEmpty()){
                String nm = ((Player)sleepingPlayersList.get(0)).getDisplayName();
                reason = RandomMessage(new String[]{
                        nm+"が寝てるのに走った",nm+"の快眠を邪魔した",nm+"が夜に騒いだ"});
                e.getPlayer().kickPlayer(reason);
            }
        }
        if(reason!=null)Bukkit.broadcastMessage(e.getPlayer().getDisplayName()+"は"+reason+"ので処罰された。");
    }


    public String RandomMessage(String[] messages){
        return messages[new Random().nextInt(messages.length-1)];
    }
}
