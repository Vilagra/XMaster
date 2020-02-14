package com.example.xmaster.data.database

import androidx.paging.DataSource
import androidx.room.*
import com.example.xmaster.data.model.Coin




@Dao
abstract class CoinsDao{

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCoins(coins: List<Coin>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertCoin(coin: Coin):Long

    @Query("SELECT count(*) FROM coin" )
    abstract suspend fun count(): Int

    @Query("UPDATE coin SET name=:name, symbol=:symbol, cmc_rank=:cmc_rank, price=:price, " +
            "circulating_supply=:circulating_supply, percent_change_24h=:percent_change_24h WHERE name = :name" )
    abstract suspend fun update(name: String,
                        symbol: String,
                        cmc_rank: Int,
                        price: Double,
                        circulating_supply: String,
                        percent_change_24h: Double)

    @Update
    abstract fun update(coins: List<Coin>)

    @Query("select * from coin order by cmc_rank")
    abstract suspend fun getAllCoins(): DataSource.Factory<Int, Coin>

    @Query("select * from coin order by cmc_rank")
    abstract suspend fun getAllCoinsList(): List<Coin>

    @Transaction
    open suspend fun insert(coins: List<Coin>){
        val isTableEmpty = count() < 1
        if(isTableEmpty) {
            insertCoins(coins)
        }
        else{
            for (coin in coins) {
                val id = insertCoin(coin)
                if (id == -1L) {
                    update(coin.name, coin.symbol, coin.cmc_rank, coin.price, coin.circulating_supply, coin.percent_change_24h)
                }
            }
        }
    }


}