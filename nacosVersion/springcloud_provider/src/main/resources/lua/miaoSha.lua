if tonumber(redis.call('get', KEYS[1])) > 0 
    then redis.call('decr', KEYS[1]); 
    redis.call('lpush', KEYS[2], ARGV[1]); 
    return 1; 
else 
    return 0; 
    end; 