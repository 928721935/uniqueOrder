local temp = 0
local key = KEYS[1]
local inc = ARGV[1]
local exp = ARGV[2]

if redis.call('EXISTS',key) == 1 then
	temp = redis.call('GET',key) + inc
	redis.call('SETEX',key,exp,temp)
	return temp
else
	temp = temp + inc
	redis.call('SETEX',key,exp,temp)
	return temp
end