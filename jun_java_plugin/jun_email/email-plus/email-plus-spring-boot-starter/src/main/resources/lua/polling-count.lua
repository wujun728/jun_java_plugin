--
-- Created by IntelliJ IDEA.
-- User: kancy
-- Date: 2020/3/6
-- Time: 9:49
-- To change this template use File | Settings | File Templates.
--
local key = KEYS[1]
local capacity = tonumber(ARGV[1])

local current = tonumber(redis.call("get", key));
if current == nil then
    current = 1;
else
    current = current + 1;
end

if current > capacity then
    redis.call("set",key, 0);
    return 1;
else
    redis.call("set",key,current);
    return 0;
end