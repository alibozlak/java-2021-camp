package bozlak.java2021.business.abstracts;

import java.util.List;

import bozlak.java2021.core.utilities.results.DataResult;
import bozlak.java2021.core.utilities.results.Result;

/**
 * Order : TAdd, TUpdate, TGet
 */
public interface BaseService<TAdd,TUpdate,TGet> {
    DataResult<List<TGet>> getAll();
    Result getById(int id);
    Result add(TAdd createEntityRequest);
    Result update(TUpdate updateEntityRequest);
    Result deleteById(int id);
}
