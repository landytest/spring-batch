/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.batch.core.jsr.partition;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.batch.api.partition.PartitionCollector;

import org.junit.Test;

public class PartitionCollectorAdapterTests {

	private PartitionCollectorAdapter adapter;

	@Test
	public void testAfterChunkSuccessful() throws Exception {
		Queue<Serializable> dataQueue = new ConcurrentLinkedQueue<Serializable>();

		adapter = new PartitionCollectorAdapter(dataQueue, new PartitionCollector() {

			private int count = 0;

			@Override
			public Serializable collectPartitionData() throws Exception {
				return String.valueOf(count++);
			}
		});

		adapter.afterChunk(null);
		adapter.afterChunk(null);
		adapter.afterChunk(null);

		assertEquals(3, dataQueue.size());
		assertEquals("0", dataQueue.remove());
		assertEquals("1", dataQueue.remove());
		assertEquals("2", dataQueue.remove());
	}
}
