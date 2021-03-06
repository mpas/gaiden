/*
 * Copyright 2013 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gaiden.command

import gaiden.GaidenConfig
import gaiden.Holders
import gaiden.exception.IllegalOperationException
import spock.lang.Specification
import spock.lang.Unroll

class CommandFactorySpec extends Specification {

    @Unroll
    def "'createCommand' should create a #commandName command"() {
        setup:
        Holders.config = Mock(GaidenConfig)
        CommandFactory factory = new CommandFactory()

        when:
        def command = factory.createCommand(commandName)

        then:
        command.class == commandClass

        where:
        commandName      | commandClass
        "build"          | Build
        "clean"          | Clean
        "create-project" | CreateProject
    }

    def "'createCommand' should throw an exception when a command is invalid"() {
        setup:
        Holders.config = Mock(GaidenConfig)
        CommandFactory factory = new CommandFactory()

        when:
        factory.createCommand(commandName)

        then:
        def e = thrown(IllegalOperationException)
        e.key == "usage"

        where:
        commandName << ["", "invalid"]
    }

}
